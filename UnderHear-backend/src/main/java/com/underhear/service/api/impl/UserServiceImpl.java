package com.underhear.service.api.impl;

import org.springframework.stereotype.Service;

import com.underhear.mapper.api.UserMapper;
import com.underhear.pojo.dto.request.UserGithubDORT;
import com.underhear.service.api.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean exists(UserGithubDORT userGithubDORT) {
        if (userGithubDORT == null) {
            return false;
        }
        if (userGithubDORT.getGithubId() != null) {
            return userMapper.countByGithubId(userGithubDORT.getGithubId()) > 0;
        }
        String login = userGithubDORT.getLogin();
        if (login == null || login.isBlank()) {
            return false;
        }
        return userMapper.countByLogin(login) > 0;
    }
}
