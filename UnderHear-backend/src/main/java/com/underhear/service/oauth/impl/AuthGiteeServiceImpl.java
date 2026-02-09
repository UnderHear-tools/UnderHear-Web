package com.underhear.service.oauth.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.underhear.converter.ToDore;
import com.underhear.converter.ToDort;
import com.underhear.converter.ToEntity;
import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.mapper.oauth.AuthGiteeMapper;
import com.underhear.pojo.dto.request.UserGiteeDort;
import com.underhear.pojo.dto.response.UserLoginWithTokenDore;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserGitee;
import com.underhear.security.JwtTokenService;
import com.underhear.security.SessionAuthService;
import com.underhear.service.api.UserService;
import com.underhear.service.oauth.AuthGiteeService;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

@Service
public class AuthGiteeServiceImpl implements AuthGiteeService {

    @Autowired
    private AuthGiteeMapper authGiteeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SessionAuthService sessionAuthService;

    @Override
    @Transactional
    // gitee oauth登录/注册
    public UserLoginWithTokenDore login(AuthResponse<AuthUser> authResponse) {
        // 校验授权结果是否成功
        if (authResponse == null || !authResponse.ok() || authResponse.getData() == null) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }
        AuthUser authUser = authResponse.getData();
        AuthToken giteeToken = authUser.getToken();
        // 转成UserGiteeDort对象
        UserGiteeDort userGiteeDort = ToDort.toUserGiteeDort(authUser.getRawUserInfo(), giteeToken);

        User user = null;

        // 如果第一次登录：注册+登录
        if (!exists(userGiteeDort)) {
            // 转成UserGitee对象
            UserGitee userGitee = ToEntity.toUserGitee(userGiteeDort);
            // 转成User对象
            user = ToEntity.toUser(userGitee);
            // 分别保存到user_gitee表和user表，并建立关联
            authGiteeMapper.saveUserGiteeAndUser(userGitee, user);
            // 生成token
            String token = jwtTokenService.generateToken(user.getUuid());
            // 将token加入白名单
            sessionAuthService.whitelistToken(token);
            // 记录登录日志
            userService.insertUserLoginRecord(user.getUuid(), "GITEE_OAUTH");
            return ToDore.toUserLoginWithTokenDore(user, token);
        }

        // 登录+更新用户信息
        // 转成UserGitee对象
        UserGitee updateUserGitee = ToEntity.toUpdateUserGitee(userGiteeDort);
        // 更新user_gitee表中的用户信息
        authGiteeMapper.updateUserGiteeByGiteeId(updateUserGitee);
        // 根据giteeId查询用户信息
        user = userService.getUserByGiteeId(userGiteeDort.getGiteeId());
        // 更新user表中的最后登录时间和最后登录方式
        userService.updateUserLastLoginByUuid(user.getUuid(), LocalDateTime.now(), "GITEE_OAUTH");
        // 转成更新后的User对象
        user = ToEntity.toUpdateUser(user, LocalDateTime.now(), "GITEE_OAUTH");
        // 生成token
        String token = jwtTokenService.generateToken(user.getUuid());
        // 将token加入白名单
        sessionAuthService.whitelistToken(token);
        // 记录登录日志
        userService.insertUserLoginRecord(user.getUuid(), "GITEE_OAUTH");
        return ToDore.toUserLoginWithTokenDore(user, token);
    }

    @Override
    // 在user_gitee表中检测该用户是否存在
    public boolean exists(UserGiteeDort userGiteeDort) {
        if (userGiteeDort == null) {
            return false;
        }
        if (userGiteeDort.getGiteeId() != null) {
            return authGiteeMapper.countByGiteeId(userGiteeDort.getGiteeId()) > 0;
        }
        return false;
    }

}
