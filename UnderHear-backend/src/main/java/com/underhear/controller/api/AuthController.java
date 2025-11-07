package com.underhear.controller.api;

import com.underhear.dto.AuthResponse;
import com.underhear.dto.GithubLoginRequest;
import com.underhear.dto.UserDto;
import com.underhear.entity.User;
import com.underhear.service.AuthService;
import com.underhear.service.GithubAuthClient;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final GithubAuthClient githubAuthClient;

    public AuthController(AuthService authService, GithubAuthClient githubAuthClient) {
        this.authService = authService;
        this.githubAuthClient = githubAuthClient;
    }

    @PostMapping("/github")
    public ResponseEntity<AuthResponse> loginWithGithub(@Valid @RequestBody GithubLoginRequest request) {
        AuthResponse response = authService.authenticateWithGithubCode(request.getCode(), request.getState());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/github/authorize")
    public ResponseEntity<Map<String, String>> getGithubAuthorizeUrl(@RequestParam(value = "state", required = false) String state) {
        String resolvedState = StringUtils.hasText(state) ? state : UUID.randomUUID().toString();
        String authorizeUrl = githubAuthClient.buildAuthorizeUrl(resolvedState);
        return ResponseEntity.ok(Map.of(
                "authorizeUrl", authorizeUrl,
                "state", resolvedState
        ));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> profile(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(toUserDto(user));
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

