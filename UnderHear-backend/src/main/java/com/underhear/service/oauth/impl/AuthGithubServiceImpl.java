package com.underhear.service.oauth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.underhear.converter.DortToEntity;
import com.underhear.converter.OtherToDort;
import com.underhear.mapper.oauth.AuthGithubMapper;
import com.underhear.pojo.dto.request.UserGithubDort;
import com.underhear.pojo.entity.UserGithub;
import com.underhear.pojo.entity.User;
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

    @Override
    @Transactional
    //github oauth登录/注册
    public String login(AuthResponse<AuthUser> authResponse) {
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
        AuthToken token = authUser.getToken();
        if (token == null || token.getAccessToken() == null || token.getAccessToken().isBlank()) {
            throw new IllegalStateException("github access token is empty");
        }
        
        UserGithubDort userGithubDort = OtherToDort.toUserGithubDort(authUser.getRawUserInfo(), token);
        
        if (!exists(userGithubDort)) {
            UserGithub userGithub = DortToEntity.toUserGithub(userGithubDort);
            User user = DortToEntity.toUser(userGithub);
            authGithubMapper.saveUserGithubAndUser(userGithub, user);
        }

        User user = userService.findUserByGithubId(userGithubDort.getGithubId());

        

        return token.getAccessToken();
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
