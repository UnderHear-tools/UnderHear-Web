package com.underhear.service.impl;

import com.underhear.entity.User;
import com.underhear.mapper.UserMapper;
import com.underhear.service.UserService;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public User upsertGithubUser(AuthUser authUser) {
        Long githubId = parseGithubId(authUser.getUuid());

        User existingUser = userMapper.findByGithubId(githubId);
        if (existingUser == null) {
            User user = mapToUserEntity(authUser, githubId);
            userMapper.insert(user);
            return user;
        }

        updateUserEntity(existingUser, authUser);
        userMapper.update(existingUser);
        return existingUser;
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    private User mapToUserEntity(AuthUser authUser, Long githubId) {
        User user = new User();
        user.setGithubId(githubId);
        applyAuthUser(user, authUser);
        return user;
    }

    private void updateUserEntity(User user, AuthUser authUser) {
        applyAuthUser(user, authUser);
    }

    private void applyAuthUser(User user, AuthUser authUser) {
        user.setLogin(authUser.getUsername());
        user.setName(authUser.getNickname());
        user.setAvatarUrl(authUser.getAvatar());
        user.setEmail(authUser.getEmail());
        user.setBio(authUser.getRemark());

        Object htmlUrl = authUser.getRawUserInfo() != null ? authUser.getRawUserInfo().get("html_url") : null;
        user.setHtmlUrl(htmlUrl instanceof String ? (String) htmlUrl : null);

        AuthToken token = authUser.getToken();
        if (token != null) {
            user.setGithubToken(token.getAccessToken());
        }
    }

    private Long parseGithubId(String uuid) {
        if (!StringUtils.hasText(uuid)) {
            throw new IllegalStateException("GitHub user id is missing");
        }
        try {
            return Long.parseLong(uuid);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException("Unexpected GitHub user id: " + uuid, ex);
        }
    }
}

