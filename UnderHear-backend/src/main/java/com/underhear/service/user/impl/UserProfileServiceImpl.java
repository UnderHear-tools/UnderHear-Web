package com.underhear.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.mapper.user.UserProfileMapper;
import com.underhear.pojo.dto.request.UserProfileDort;
import com.underhear.pojo.dto.request.UserProfileMarkdownDort;
import com.underhear.pojo.entity.User;
import com.underhear.pojo.entity.UserProfileMarkdown;
import com.underhear.service.user.UserProfileService;
import com.underhear.util.StringNormalizer;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    // 公开资料页使用昵称作为稳定入口，查询不到时复用用户不存在业务错误。
    public User getUserByNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        User user = userProfileMapper.getUserByNickname(nickname);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    // Markdown 属于公开资料页扩展内容，没有记录时由 controller 转换为 markdown: null。
    public UserProfileMarkdown getMarkdownByUuid(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        return userProfileMapper.getMarkdownByUuid(uuid);
    }

    @Override
    // 保存时只使用登录态用户 uuid，避免请求体越权写入其他用户资料。
    public void saveCurrentUserMarkdown(User user, UserProfileMarkdownDort request) {
        if (user == null || user.getUuid() == null || user.getUuid().isBlank() || request == null) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        int affectedRows = userProfileMapper.upsertMarkdown(user.getUuid(), request.getContent());
        if (affectedRows <= 0) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    @CacheEvict(cacheNames = "user:info", key = "#user.uuid")
    // 保存时只使用登录态用户 uuid，资料字段统一裁剪空白，空字符串存为 null。
    public User saveCurrentUserProfile(User user, UserProfileDort request) {
        if (user == null || user.getUuid() == null || user.getUuid().isBlank() || request == null) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }
        String bio = StringNormalizer.normalizeNullable(request.getBio());
        String pronoun = StringNormalizer.normalizeNullable(request.getPronoun());
        String location = StringNormalizer.normalizeNullable(request.getLocation());
        String socialAccount0 = StringNormalizer.normalizeNullable(request.getSocialAccount0());
        String socialAccount1 = StringNormalizer.normalizeNullable(request.getSocialAccount1());
        String socialAccount2 = StringNormalizer.normalizeNullable(request.getSocialAccount2());

        int affectedRows = userProfileMapper.updateCurrentUserProfile(
                user.getUuid(),
                bio,
                pronoun,
                location,
                socialAccount0,
                socialAccount1,
                socialAccount2);
        if (affectedRows != 1) {
            throw new BizException(ErrorCode.INTERNAL_ERROR);
        }

        user.setBio(bio);
        user.setPronoun(pronoun);
        user.setLocation(location);
        user.setSocialAccount0(socialAccount0);
        user.setSocialAccount1(socialAccount1);
        user.setSocialAccount2(socialAccount2);
        return user;
    }
}
