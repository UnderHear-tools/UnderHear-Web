package com.underhear.service.oauth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.underhear.converter.ToDore;
import com.underhear.converter.ToDort;
import com.underhear.converter.ToEntity;
import com.underhear.mapper.oauth.AuthGithubMapper;
import com.underhear.pojo.dto.request.UserGithubDort;
import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.entity.UserGithub;
import com.underhear.pojo.entity.User;
import com.underhear.security.JwtTokenService;
import com.underhear.service.api.UserService;
import com.underhear.service.oauth.AuthGithubService;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

@Service
public class AuthGithubServiceImpl implements AuthGithubService {

    @Autowired
    private AuthGithubMapper authGithubMapper;
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    @Transactional
    //github oauth登录/注册
    public UserLoginDore login(AuthResponse<AuthUser> authResponse) {
        if (authResponse == null) {
            throw new IllegalArgumentException("authResponse is null");
        }
        if (!authResponse.ok()) {
            String message = authResponse.getMsg() == null ? "unknown error" : authResponse.getMsg();
            throw new IllegalStateException("github auth failed: " + message);
        }
        AuthUser authUser = authResponse.getData();
        if (authUser == null) {
            throw new IllegalStateException("github user info is empty");
        }
        AuthToken githubToken = authUser.getToken();
        if (githubToken == null || githubToken.getAccessToken() == null || githubToken.getAccessToken().isBlank()) {
            throw new IllegalStateException("github access token is empty");
        }
        
        UserGithubDort userGithubDort = ToDort.toUserGithubDort(authUser.getRawUserInfo(), githubToken);
        
        User user = null;

        //如果不存在：注册+登录
        if (!exists(userGithubDort)) {
            UserGithub userGithub = ToEntity.toUserGithub(userGithubDort);
            user = ToEntity.toUser(userGithub);
            authGithubMapper.saveUserGithubAndUser(userGithub, user);
            String token = jwtTokenService.generateToken(user.getUuid());
            return ToDore.toUserLoginDore(user, token);
        }

        //登录
        user = userService.getUserByGithubId(userGithubDort.getGithubId());
        String token = jwtTokenService.generateToken(user.getUuid());
        return ToDore.toUserLoginDore(user, token);
    }

    @Override
    //在oauth_github表中检测该用户是否存在
    public boolean exists(UserGithubDort userGithubDort) {
        if (userGithubDort == null) {
            return false;
        }
        if (userGithubDort.getGithubId() != null) {
            return authGithubMapper.countByGithubId(userGithubDort.getGithubId()) > 0;
        }
        return false;
    }

}
