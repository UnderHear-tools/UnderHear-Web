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
import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserGitee;
import com.underhear.security.JwtTokenService;
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

    @Override
    @Transactional
    //gitee oauth登录/注册
    public UserLoginDore login(AuthResponse<AuthUser> authResponse) {
        if (authResponse == null || !authResponse.ok() || authResponse.getData() == null) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }
        AuthUser authUser = authResponse.getData();
        AuthToken giteeToken = authUser.getToken();
        String accessToken = giteeToken == null ? null : giteeToken.getAccessToken();
        if (accessToken == null || accessToken.isBlank()) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }
        
        UserGiteeDort userGiteeDort = ToDort.toUserGiteeDort(authUser.getRawUserInfo(), giteeToken);
        
        User user = null;

        //如果第一次登录：注册+登录
        if (!exists(userGiteeDort)) {
            UserGitee userGitee = ToEntity.toUserGitee(userGiteeDort);
            user = ToEntity.toUser(userGitee);
            authGiteeMapper.saveUserGiteeAndUser(userGitee, user);
            String token = jwtTokenService.generateToken(user.getUuid());
            userService.insertUserLoginRecord(user.getUuid(), "GITEE_OAUTH");
            return ToDore.toUserLoginDore(user, token);
        }

        //登录+更新用户信息
        UserGitee updateUserGitee = ToEntity.toUpdateUserGitee(userGiteeDort);
        updateUserGitee.setGiteeId(userGiteeDort.getGiteeId());
        authGiteeMapper.updateUserGiteeByGiteeId(updateUserGitee);
        user = userService.getUserByGiteeId(userGiteeDort.getGiteeId());
        userService.updateUserLastLoginByUuid(user.getUuid(),LocalDateTime.now(),"GITEE_OAUTH");
        user = ToEntity.toUpdateUser(user,LocalDateTime.now(),"GITEE_OAUTH");
        String token = jwtTokenService.generateToken(user.getUuid());
        userService.insertUserLoginRecord(user.getUuid(), "GITEE_OAUTH");
        return ToDore.toUserLoginDore(user, token);
    }

    @Override
    //在user_gitee表中检测该用户是否存在
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
