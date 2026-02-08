package com.underhear.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.underhear.exception.BizException;
import com.underhear.exception.ErrorCode;
import com.underhear.pojo.entity.User;
import com.underhear.service.api.UserService;

@Service
public class SessionAuthService {

    // Redis 白名单 key 前缀 完整格式 auth:token:{jti}
    private static final String REDIS_KEY_PREFIX = "auth:token:";

    private final JwtTokenService jwtTokenService;
    private final StringRedisTemplate stringRedisTemplate;
    private final UserService userService;
    private final long tokenExpireSeconds;

    public SessionAuthService(
            JwtTokenService jwtTokenService,
            StringRedisTemplate stringRedisTemplate,
            UserService userService,
            @Value("${security.jwt.expire-seconds}") long tokenExpireSeconds) {
        this.jwtTokenService = jwtTokenService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.userService = userService;
        this.tokenExpireSeconds = tokenExpireSeconds;
    }

    // 登录成功后写入白名单 表示该 token 可以使用
    public void whitelistToken(String token) {
        JwtTokenService.JwtTokenPayload payload = parseToken(token);
        String tokenId = requireValue(payload.getTokenId());
        String uuid = requireValue(payload.getUuid());
        stringRedisTemplate.opsForValue().set(
                redisKey(tokenId),
                uuid,
                tokenExpireSeconds,
                TimeUnit.SECONDS);
    }

    // 根据 token 获取当前用户 要求 token 合法且仍在白名单里
    public User getCurrentUser(String token) {
        JwtTokenService.JwtTokenPayload payload = parseToken(token);
        String tokenId = requireValue(payload.getTokenId());
        String uuid = stringRedisTemplate.opsForValue().get(redisKey(tokenId));
        if (uuid == null) {
            // 白名单中没有该 token 视为未登录
            throw new BizException(ErrorCode.UNAUTHORIZED);
        }
        User user = userService.getUserByUuid(uuid);
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    // 退出登录 删除白名单记录 让当前 token 立即失效
    public void logout(String token) {
        JwtTokenService.JwtTokenPayload payload = parseToken(token);
        String tokenId = requireValue(payload.getTokenId());
        stringRedisTemplate.delete(redisKey(tokenId));
    }

    // 如果已经有 token 还请求登录接口 就先把之前的 token 失效掉 避免同一用户多个 token 共存
    public void logoutIfPresent(String token) {
        if (token != null) {
            logout(token);
        }
    }

    // 统一做 JWT 解析 解析失败按未登录处理
    private JwtTokenService.JwtTokenPayload parseToken(String token) {
        try {
            return jwtTokenService.parseToken(token);
        } catch (Exception ex) {
            throw new BizException(ErrorCode.UNAUTHORIZED);
        }
    }

    // 拼接 Redis key
    @NonNull
    private String redisKey(String tokenId) {
        return REDIS_KEY_PREFIX + tokenId;
    }

    // 把可能为 null 的值收窄为非空
    @NonNull
    private String requireValue(String value) {
        if (value == null) {
            throw new BizException(ErrorCode.UNAUTHORIZED);
        }
        return value;
    }
}
