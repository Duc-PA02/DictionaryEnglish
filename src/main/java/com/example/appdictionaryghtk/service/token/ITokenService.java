package com.example.appdictionaryghtk.service.token;

import com.example.appdictionaryghtk.entity.Token;
import com.example.appdictionaryghtk.entity.User;

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user);
}
