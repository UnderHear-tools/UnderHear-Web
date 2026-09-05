package com.onlikee.module.auth.oauth.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlikee.module.auth.oauth.converter.ToDTO;
import com.onlikee.module.auth.oauth.converter.ToEntity;
import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.module.auth.oauth.mapper.AuthGithubMapper;
import com.onlikee.module.auth.oauth.model.dto.UserGithubDTO;
import com.onlikee.module.auth.oauth.model.dto.OAuthCallbackWithTokenDTO;
import com.onlikee.module.auth.oauth.model.dto.OAuthPendingSignupResultDTO;
import com.onlikee.module.auth.oauth.model.entity.UserGithubEntity;
import com.onlikee.module.user.model.entity.UserEntity;
import com.onlikee.module.auth.service.JwtTokenService;
import com.onlikee.module.auth.service.SessionAuthService;
import com.onlikee.module.user.service.UserService;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;

@Service
public class AuthGithubService {

    @Autowired
    private AuthGithubMapper authGithubMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SessionAuthService sessionAuthService;

    @Autowired
    private OAuthSignupService oauthSignupService;

    @Transactional
    //github oauth登录/注册
    public OAuthCallbackWithTokenDTO login(AuthResponse<AuthUser> authResponse) {
        //校验授权结果是否成功
        if (authResponse == null || !authResponse.ok() || authResponse.getData() == null) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }
        AuthUser authUser = authResponse.getData();
        AuthToken githubToken = authUser.getToken();
        //转成UserGithubDTO对象
        UserGithubDTO userGithubDTO = ToDTO.toUserGithubDTO(authUser.getRawUserInfo(), githubToken);
        if (userGithubDTO.getGithubId() == null) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }

        UserEntity user = null;

        //如果第一次登录：注册+登录
        if (!exists(userGithubDTO)) {
            // 首次 OAuth 只创建短期注册会话，昵称和邮箱由用户在完善资料页确认。
            OAuthPendingSignupResultDTO pendingSignup = oauthSignupService.createGithubPendingSignup(userGithubDTO);
            return OAuthCallbackWithTokenDTO.signupRequired(pendingSignup);
        }

        //登录+更新用户信息
        //转成UserGithub对象
        UserGithubEntity updateUserGithub = ToEntity.toUpdateUserGithubEntity(userGithubDTO);
        //更新user_github表中的用户信息
        authGithubMapper.updateUserGithubByGithubId(updateUserGithub);
        //根据githubId查询用户信息
        user = userService.getUserByGithubId(userGithubDTO.getGithubId());
        //更新user表中的最后登录时间和最后登录方式
        userService.updateUserLastLoginByUuid(user.getUuid(),LocalDateTime.now(),"GITHUB_OAUTH");
        //转成更新后的User对象
        user = com.onlikee.module.user.converter.ToEntity.toUpdateUserEntity(user,LocalDateTime.now(),"GITHUB_OAUTH");
        //生成token
        String token = jwtTokenService.generateToken(user.getUuid());
        //将token加入白名单
        sessionAuthService.whitelistToken(token);
        //记录登录日志
        userService.insertUserLoginRecord(user.getUuid(), "GITHUB_OAUTH");
        return OAuthCallbackWithTokenDTO.loginSuccess(com.onlikee.module.auth.converter.ToDTO.toUserLoginWithTokenDTO(user, token));
    }

    //在oauth_github表中检测该用户是否存在
    public boolean exists(UserGithubDTO userGithubDTO) {
        if (userGithubDTO == null) {
            return false;
        }
        if (userGithubDTO.getGithubId() != null) {
            return authGithubMapper.countByGithubId(userGithubDTO.getGithubId()) > 0;
        }
        return false;
    }

}
