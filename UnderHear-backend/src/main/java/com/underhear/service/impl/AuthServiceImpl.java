package com.underhear.service.impl;

import com.underhear.dto.AuthResponse;
import com.underhear.dto.UserDto;
import com.underhear.entity.User;
import com.underhear.service.AuthService;
import com.underhear.service.GithubAuthClient;
import com.underhear.service.UserService;
import com.underhear.util.JwtTokenProvider;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final GithubAuthClient githubAuthClient;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(GithubAuthClient githubAuthClient,
                           UserService userService,
                           JwtTokenProvider jwtTokenProvider) {
        this.githubAuthClient = githubAuthClient;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public AuthResponse authenticateWithGithubCode(String code, String state) {
        me.zhyd.oauth.model.AuthResponse<AuthUser> response = githubAuthClient.login(code, state);
        if (response == null || response.getCode() != AuthResponseStatus.SUCCESS.getCode() || response.getData() == null) {
            String message = response != null ? response.getMsg() : "Unknown error";
            throw new IllegalStateException("GitHub authentication failed: " + message);
        }

        AuthUser authUser = response.getData();
        User user = userService.upsertGithubUser(authUser);
        String jwt = jwtTokenProvider.generateToken(user.getId());
        return new AuthResponse(jwt, toUserDto(user));
    }

    private UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setGithubId(user.getGithubId());
        dto.setLogin(user.getLogin());
        dto.setName(user.getName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setHtmlUrl(user.getHtmlUrl());
        return dto;
    }
}

