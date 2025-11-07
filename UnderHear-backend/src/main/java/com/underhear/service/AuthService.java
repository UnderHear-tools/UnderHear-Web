package com.underhear.service;

import com.underhear.dto.AuthResponse;

public interface AuthService {

    AuthResponse authenticateWithGithubCode(String code, String state);
}

