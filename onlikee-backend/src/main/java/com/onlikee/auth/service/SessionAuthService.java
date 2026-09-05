package com.onlikee.auth.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.onlikee.common.exception.BizException;
import com.onlikee.common.exception.ErrorCode;
import com.onlikee.user.model.entity.User;
import com.onlikee.user.service.UserService;

@Service
public class SessionAuthService {

    // Redis 白名单 key 前缀 完整格式 auth:token:{jti}
    private static final String REDIS_KEY_PREFIX = "auth:token:";
    private static final String USER_TOKENS_KEY_PREFIX = "auth:user:tokens:";

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
        String tokenId = payload.getTokenId();
        String uuid = payload.getUuid();
        stringRedisTemplate.opsForValue().set(
                tokensKey(tokenId),
                uuid,
                tokenExpireSeconds,
                TimeUnit.SECONDS);
        String userTokensKey = userTokensKey(uuid);
        stringRedisTemplate.opsForSet().add(userTokensKey, tokenId);
        stringRedisTemplate.expire(userTokensKey, tokenExpireSeconds, TimeUnit.SECONDS);
    }

    // 根据 token 获取当前用户 要求 token 合法且仍在白名单里
    public User getCurrentUser(String token) {
        JwtTokenService.JwtTokenPayload payload = parseToken(token);
        String tokenId = payload.getTokenId();
        String uuid = stringRedisTemplate.opsForValue().get(tokensKey(tokenId));
        if (uuid == null) {
            // 白名单中没有该 token 视为未登录
            throw new BizException(ErrorCode.NOT_LOGIN);
        }
        return userService.getUserByUuid(uuid);
    }

    // 退出登录 删除白名单记录 让当前 token 立即失效
    public void logout(String token) {
        JwtTokenService.JwtTokenPayload payload = parseToken(token);
        String tokenId = payload.getTokenId();
        String uuid = payload.getUuid();
        stringRedisTemplate.delete(tokensKey(tokenId));
        stringRedisTemplate.opsForSet().remove(userTokensKey(uuid), tokenId);
    }

    // 使同一用户的所有 token 失效 适用于修改密码等安全敏感操作后强制下线
    public void logoutAll(String token) {
        JwtTokenService.JwtTokenPayload payload = parseToken(token);
        String uuid = payload.getUuid();
        String userTokensKey = userTokensKey(uuid);
        Set<String> tokenIds = stringRedisTemplate.opsForSet().members(userTokensKey);
        if (tokenIds != null && !tokenIds.isEmpty()) {
            stringRedisTemplate.delete(tokenIds.stream().map(this::tokensKey).collect(Collectors.toSet()));
        }
        stringRedisTemplate.delete(userTokensKey);
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
            throw new BizException(ErrorCode.NOT_LOGIN);
        }
    }

    // 拼接 Redis key
    private String tokensKey(String tokenId) {
        return REDIS_KEY_PREFIX + tokenId;
    }

    private String userTokensKey(String uuid) {
        return USER_TOKENS_KEY_PREFIX + uuid;
    }
}
