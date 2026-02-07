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
import com.underhear.mapper.oauth.AuthGithubMapper;
import com.underhear.pojo.dto.request.UserGithubDort;
import com.underhear.pojo.dto.response.UserLoginDore;
import com.underhear.pojo.entity.UserGithub;
import com.underhear.pojo.entity.User;
import com.underhear.security.JwtTokenService;
import com.underhear.security.SessionAuthService;
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

    @Autowired
    private SessionAuthService sessionAuthService;

    @Override
    @Transactional
    //github oauth登录/注册
    public UserLoginDore login(AuthResponse<AuthUser> authResponse) {
        //校验授权结果是否成功
        if (authResponse == null || !authResponse.ok() || authResponse.getData() == null) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }
        AuthUser authUser = authResponse.getData();
        AuthToken githubToken = authUser.getToken();
        //转成UserGithubDort对象
        UserGithubDort userGithubDort = ToDort.toUserGithubDort(authUser.getRawUserInfo(), githubToken);
        
        User user = null;

        //如果第一次登录：注册+登录
        if (!exists(userGithubDort)) {
            //转成UserGithub对象
            UserGithub userGithub = ToEntity.toUserGithub(userGithubDort);
            //转成User对象
            user = ToEntity.toUser(userGithub);
            //分别保存到user_github表和user表，并建立关联
            authGithubMapper.saveUserGithubAndUser(userGithub, user);
            //生成token
            String token = jwtTokenService.generateToken(user.getUuid());
            //将token加入白名单
            sessionAuthService.whitelistToken(token);
            //记录登录日志
            userService.insertUserLoginRecord(user.getUuid(), "GITHUB_OAUTH");
            return ToDore.toUserLoginDore(user, token);
        }

        //登录+更新用户信息
        //转成UserGithub对象
        UserGithub updateUserGithub = ToEntity.toUpdateUserGithub(userGithubDort);
        //更新user_github表中的用户信息
        authGithubMapper.updateUserGithubByGithubId(updateUserGithub);
        //根据githubId查询用户信息
        user = userService.getUserByGithubId(userGithubDort.getGithubId());
        //更新user表中的最后登录时间和最后登录方式
        userService.updateUserLastLoginByUuid(user.getUuid(),LocalDateTime.now(),"GITHUB_OAUTH");
        //转成更新后的User对象
        user = ToEntity.toUpdateUser(user,LocalDateTime.now(),"GITHUB_OAUTH");
        //生成token
        String token = jwtTokenService.generateToken(user.getUuid());
        //将token加入白名单
        sessionAuthService.whitelistToken(token);
        //记录登录日志
        userService.insertUserLoginRecord(user.getUuid(), "GITHUB_OAUTH");
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
