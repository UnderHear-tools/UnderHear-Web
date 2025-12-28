package com.underhear.service.oauth.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.underhear.converter.ToDore;
import com.underhear.converter.ToDort;
import com.underhear.converter.ToEntity;
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
        if (authResponse == null) {
            throw new IllegalArgumentException("authResponse is null");
        }
        if (!authResponse.ok()) {
            String message = authResponse.getMsg() == null ? "unknown error" : authResponse.getMsg();
            throw new IllegalStateException("gitee auth failed: " + message);
        }
        AuthUser authUser = authResponse.getData();
        if (authUser == null) {
            throw new IllegalStateException("gitee user info is empty");
        }
        AuthToken giteeToken = authUser.getToken();
        if (giteeToken == null || giteeToken.getAccessToken() == null || giteeToken.getAccessToken().isBlank()) {
            throw new IllegalStateException("gitee access token is empty");
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
