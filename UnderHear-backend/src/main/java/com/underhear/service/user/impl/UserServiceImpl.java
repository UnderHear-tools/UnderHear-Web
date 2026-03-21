package com.underhear.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.underhear.mapper.user.UserMapper;
import com.underhear.pojo.entity.User;
import com.underhear.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(cacheNames = "user:info", key = "#uuid")
    // 閫氳繃 UUID 鏌ョ敤鎴凤紙鍏堣 Redis锛屾湭鍛戒腑鍐嶆煡鏁版嵁搴擄級
    public User getUserByUuid(String uuid) {
        return userMapper.getUserByUuid(uuid);
    }

    @Override
    // 閫氳繃 GitHub ID 鏌ョ敤鎴?
    public User getUserByGithubId(Long githubId) {
        if (githubId == null) {
            return null;
        }
        return userMapper.getUserByGithubId(githubId);
    }

    @Override
    // 閫氳繃 Gitee ID 鏌ョ敤鎴?
    public User getUserByGiteeId(Long giteeId) {
        if (giteeId == null) {
            return null;
        }
        return userMapper.getUserByGiteeId(giteeId);
    }

    @Override
    // 鏇存柊鏈€鍚庣櫥褰曚俊鎭?
    public int updateUserLastLoginByUuid(String uuid, java.time.LocalDateTime lastLoginAt, String lastLoginSource) {
        if (uuid == null || uuid.isBlank()) {
            return 0;
        }
        return userMapper.updateUserLastLoginByUuid(uuid, lastLoginAt, lastLoginSource);
    }

    @Override
    // 璁板綍涓€娆＄櫥褰曟潵婧?
    public int insertUserLoginRecord(String uuid, String loginSource) {
        if (uuid == null || uuid.isBlank() || loginSource == null || loginSource.isBlank()) {
            return 0;
        }
        return userMapper.insertUserLoginRecord(uuid, loginSource);
    }
}
