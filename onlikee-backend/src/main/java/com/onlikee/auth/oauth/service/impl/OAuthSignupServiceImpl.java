package com.onlikee.auth.oauth.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.onlikee.auth.converter.ToDore;
import com.onlikee.auth.oauth.converter.ToEntity;
import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.auth.oauth.mapper.AuthGiteeMapper;
import com.onlikee.auth.oauth.mapper.AuthGithubMapper;
import com.onlikee.user.mapper.UserMapper;
import com.onlikee.auth.oauth.model.dto.request.OAuthPendingSignupDort;
import com.onlikee.auth.oauth.model.dto.request.OAuthSignupCompleteDort;
import com.onlikee.auth.oauth.model.dto.request.UserGiteeDort;
import com.onlikee.auth.oauth.model.dto.request.UserGithubDort;
import com.onlikee.auth.oauth.model.dto.response.OAuthPendingSignupDore;
import com.onlikee.auth.model.dto.response.UserLoginWithTokenDore;
import com.onlikee.user.model.entity.User;
import com.onlikee.auth.oauth.model.entity.UserGitee;
import com.onlikee.auth.oauth.model.entity.UserGithub;
import com.onlikee.auth.service.JwtTokenService;
import com.onlikee.auth.service.SessionAuthService;
import com.onlikee.auth.oauth.service.OAuthSignupService;
import com.onlikee.user.service.UserService;
import com.onlikee.common.util.ShortUuidGenerator;

@Service
public class OAuthSignupServiceImpl implements OAuthSignupService {

    private static final String PROVIDER_GITHUB = "github";
    private static final String PROVIDER_GITEE = "gitee";
    private static final String PENDING_KEY_PREFIX = "oauth:pending_signup:";
    private static final String LOCK_KEY_PREFIX = "oauth:pending_signup:lock:";
    private static final long PENDING_EXPIRE_SECONDS = 30L * 60;
    private static final long LOCK_EXPIRE_SECONDS = 30L;
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_-]{1,20}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthGithubMapper authGithubMapper;

    @Autowired
    private AuthGiteeMapper authGiteeMapper;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private SessionAuthService sessionAuthService;

    @Autowired
    private UserService userService;

    @Override
    public OAuthPendingSignupDore createGithubPendingSignup(UserGithubDort userGithubDort) {
        if (userGithubDort == null || userGithubDort.getGithubId() == null) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }
        OAuthPendingSignupDort pendingSignup = new OAuthPendingSignupDort();
        pendingSignup.setProvider(PROVIDER_GITHUB);
        pendingSignup.setProviderUserId(userGithubDort.getGithubId());
        pendingSignup.setName(userGithubDort.getName());
        pendingSignup.setAvatarUrl(userGithubDort.getAvatarUrl());
        pendingSignup.setEmail(userGithubDort.getEmail());
        pendingSignup.setBio(userGithubDort.getBio());
        pendingSignup.setHtmlUrl(userGithubDort.getHtmlUrl());
        pendingSignup.setProviderToken(userGithubDort.getGithubToken());
        return savePendingSignup(pendingSignup);
    }

    @Override
    public OAuthPendingSignupDore createGiteePendingSignup(UserGiteeDort userGiteeDort) {
        if (userGiteeDort == null || userGiteeDort.getGiteeId() == null) {
            throw new BizException(ErrorCode.BAD_AUTHORIZED);
        }
        OAuthPendingSignupDort pendingSignup = new OAuthPendingSignupDort();
        pendingSignup.setProvider(PROVIDER_GITEE);
        pendingSignup.setProviderUserId(userGiteeDort.getGiteeId());
        pendingSignup.setName(userGiteeDort.getName());
        pendingSignup.setAvatarUrl(userGiteeDort.getAvatarUrl());
        pendingSignup.setEmail(userGiteeDort.getEmail());
        pendingSignup.setBio(userGiteeDort.getBio());
        pendingSignup.setHtmlUrl(userGiteeDort.getHtmlUrl());
        pendingSignup.setProviderToken(userGiteeDort.getGiteeToken());
        return savePendingSignup(pendingSignup);
    }

    @Override
    @Transactional
    public UserLoginWithTokenDore complete(OAuthSignupCompleteDort request) {
        String pendingSignupToken = normalize(request.getPendingSignupToken());
        String lockKey = lockKey(pendingSignupToken);
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "1", LOCK_EXPIRE_SECONDS, TimeUnit.SECONDS);
        if (!Boolean.TRUE.equals(locked)) {
            throw new BizException(ErrorCode.PENDING_SIGNUP_IN_PROGRESS);
        }

        boolean completed = false;
        try {
            OAuthPendingSignupDort pendingSignup = getPendingSignup(pendingSignupToken);
            String nickname = normalizeNickname(request.getNickname());
            String email = normalizeEmail(request.getEmail());
            ensureUniqueUserFields(nickname, email);
            ensureOAuthAccountAvailable(pendingSignup);

            String uuid = ShortUuidGenerator.next();
            User user = ToEntity.toOAuthSignupUser(pendingSignup, uuid, nickname, email);
            insertUserAndOAuthBinding(pendingSignup, user);

            String token = jwtTokenService.generateToken(user.getUuid());
            sessionAuthService.whitelistToken(token);
            userService.insertUserLoginRecord(user.getUuid(), user.getLastLoginSource());
            stringRedisTemplate.delete(pendingKey(pendingSignupToken));
            completed = true;
            return ToDore.toUserLoginWithTokenDore(user, token);
        } finally {
            // 成功时保留短锁到 TTL，避免事务提交前出现第二次提交；失败时允许用户修改后重试。
            if (!completed) {
                stringRedisTemplate.delete(lockKey);
            }
        }
    }

    private OAuthPendingSignupDore savePendingSignup(OAuthPendingSignupDort pendingSignup) {
        String pendingSignupToken = UUID.randomUUID().toString().replace("-", "");
        stringRedisTemplate.opsForValue().set(
                pendingKey(pendingSignupToken),
                JSON.toJSONString(pendingSignup),
                PENDING_EXPIRE_SECONDS,
                TimeUnit.SECONDS);

        OAuthPendingSignupDore response = new OAuthPendingSignupDore();
        response.setPendingSignupToken(pendingSignupToken);
        response.setProvider(pendingSignup.getProvider());
        response.setAvatarUrl(pendingSignup.getAvatarUrl());
        response.setSuggestedNickname(suggestNickname(pendingSignup));
        response.setEmail(pendingSignup.getEmail());
        return response;
    }

    private OAuthPendingSignupDort getPendingSignup(String pendingSignupToken) {
        String pendingJson = stringRedisTemplate.opsForValue().get(pendingKey(pendingSignupToken));
        if (pendingJson == null || pendingJson.isBlank()) {
            throw new BizException(ErrorCode.PENDING_SIGNUP_INVALID);
        }
        try {
            return JSON.parseObject(pendingJson, OAuthPendingSignupDort.class);
        } catch (RuntimeException ex) {
            throw new BizException(ErrorCode.PENDING_SIGNUP_INVALID);
        }
    }

    private void insertUserAndOAuthBinding(OAuthPendingSignupDort pendingSignup, User user) {
        try {
            if (userMapper.insertUser(user) != 1) {
                throw new BizException(ErrorCode.INTERNAL_ERROR);
            }
            if (PROVIDER_GITHUB.equals(pendingSignup.getProvider())) {
                UserGithub userGithub = ToEntity.toUserGithub(pendingSignup, user.getUuid());
                if (authGithubMapper.insertUserGithub(userGithub) != 1) {
                    throw new BizException(ErrorCode.INTERNAL_ERROR);
                }
                return;
            }
            if (PROVIDER_GITEE.equals(pendingSignup.getProvider())) {
                UserGitee userGitee = ToEntity.toUserGitee(pendingSignup, user.getUuid());
                if (authGiteeMapper.insertUserGitee(userGitee) != 1) {
                    throw new BizException(ErrorCode.INTERNAL_ERROR);
                }
                return;
            }
        } catch (DuplicateKeyException ex) {
            throwDuplicateKeyBizException(ex);
        }
        throw new BizException(ErrorCode.BAD_AUTHORIZED);
    }

    private void ensureUniqueUserFields(String nickname, String email) {
        if (userMapper.countByNickname(nickname) > 0) {
            throw new BizException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }
        if (userMapper.countByEmail(email) > 0) {
            throw new BizException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void ensureOAuthAccountAvailable(OAuthPendingSignupDort pendingSignup) {
        if (PROVIDER_GITHUB.equals(pendingSignup.getProvider())
                && authGithubMapper.countByGithubId(pendingSignup.getProviderUserId()) > 0) {
            throw new BizException(ErrorCode.OAUTH_ACCOUNT_ALREADY_BOUND);
        }
        if (PROVIDER_GITEE.equals(pendingSignup.getProvider())
                && authGiteeMapper.countByGiteeId(pendingSignup.getProviderUserId()) > 0) {
            throw new BizException(ErrorCode.OAUTH_ACCOUNT_ALREADY_BOUND);
        }
    }

    private String normalizeNickname(String nickname) {
        String normalized = normalize(nickname);
        if (!NICKNAME_PATTERN.matcher(normalized).matches()) {
            throw new BizException(ErrorCode.VALIDATION_FAILED);
        }
        return normalized;
    }

    private String normalizeEmail(String email) {
        String normalized = normalize(email);
        if (normalized.length() > 50 || !EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new BizException(ErrorCode.VALIDATION_FAILED);
        }
        return normalized;
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    private String suggestNickname(OAuthPendingSignupDort pendingSignup) {
        String base = normalize(pendingSignup.getName()).replaceAll("[^A-Za-z0-9_-]", "");
        if (base.isBlank()) {
            base = pendingSignup.getProvider() + pendingSignup.getProviderUserId();
        }
        if (base.length() > 20) {
            return base.substring(0, 20);
        }
        return base;
    }

    private void throwDuplicateKeyBizException(DuplicateKeyException ex) {
        String message = ex.getMessage();
        if (message != null && (message.contains("uk_user_nickname") || message.contains("nickname"))) {
            throw new BizException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }
        if (message != null && (message.contains("uk_user_email") || message.contains("email"))) {
            throw new BizException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (message != null && (message.contains("github_id") || message.contains("gitee_id"))) {
            throw new BizException(ErrorCode.OAUTH_ACCOUNT_ALREADY_BOUND);
        }
        throw new BizException(ErrorCode.INTERNAL_ERROR);
    }

    private String pendingKey(String pendingSignupToken) {
        return PENDING_KEY_PREFIX + pendingSignupToken;
    }

    private String lockKey(String pendingSignupToken) {
        return LOCK_KEY_PREFIX + pendingSignupToken;
    }
}
