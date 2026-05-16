package com.onlikee.service.oauth.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlikee.converter.ToDore;
import com.onlikee.converter.ToDort;
import com.onlikee.converter.ToEntity;
import com.onlikee.exception.BizException;
import com.onlikee.exception.ErrorCode;
import com.onlikee.mapper.oauth.AuthGiteeMapper;
import com.onlikee.pojo.dto.request.UserGiteeDort;
import com.onlikee.pojo.dto.response.OAuthCallbackWithTokenDore;
import com.onlikee.pojo.dto.response.OAuthPendingSignupDore;
import com.onlikee.pojo.entity.User;
import com.onlikee.pojo.entity.UserGitee;
import com.onlikee.security.JwtTokenService;
import com.onlikee.security.SessionAuthService;
import com.onlikee.service.user.UserService;
import com.onlikee.service.oauth.AuthGiteeService;
import com.onlikee.service.oauth.OAuthSignupService;

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

    @Autowired
    private OAuthSignupService oauthSignupService;

    @Override
    @Transactional
    // gitee oauth登录/注册
    public OAuthCallbackWithTokenDore login(AuthResponse<AuthUser> authResponse) {
        // 校验授权结果是否成功
        if (authResponse == null || !authResponse.ok() || authResponse.getData() == null) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }
        AuthUser authUser = authResponse.getData();
        AuthToken giteeToken = authUser.getToken();
        // 转成UserGiteeDort对象
        UserGiteeDort userGiteeDort = ToDort.toUserGiteeDort(authUser.getRawUserInfo(), giteeToken);
        if (userGiteeDort.getGiteeId() == null) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }

        User user = null;

        // 如果第一次登录：注册+登录
        if (!exists(userGiteeDort)) {
            // 首次 OAuth 只创建短期注册会话，昵称和邮箱由用户在完善资料页确认。
            OAuthPendingSignupDore pendingSignup = oauthSignupService.createGiteePendingSignup(userGiteeDort);
            return OAuthCallbackWithTokenDore.signupRequired(pendingSignup);
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
        return OAuthCallbackWithTokenDore.loginSuccess(ToDore.toUserLoginWithTokenDore(user, token));
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
