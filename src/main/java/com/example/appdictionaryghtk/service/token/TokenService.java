package com.example.appdictionaryghtk.service.token;

import com.example.appdictionaryghtk.component.JwtTokenUtils;
import com.example.appdictionaryghtk.entity.Token;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.exceptions.ExpiredTokenException;
import com.example.appdictionaryghtk.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService{
    private static final int MAX_TOKENS = 3;
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    private final TokenRepository tokenRepository;
    private final JwtTokenUtils jwtTokenUtil;
    @Transactional
    @Override
    public Token addToken(User user, String token, boolean isMobileDevice) {
        List<Token> userTokens = tokenRepository.findByUser(user);
        int tokenCount = userTokens.size();

        // Số lượng token vượt quá giới hạn, xóa một token cũ
        if (tokenCount >= MAX_TOKENS) {
            // Tìm tất cả các token không phải là mobile
            List<Token> nonMobileTokens = userTokens.stream()
                    .filter(userToken -> !userToken.isMobile())
                    .collect(Collectors.toList());

            Token tokenToDelete;

            if (!nonMobileTokens.isEmpty()) {
                // Nếu có token không phải là mobile, xóa token có ngày hết hạn gần nhất trong số đó
                tokenToDelete = nonMobileTokens.stream()
                        .min(Comparator.comparing(Token::getExpirationDate))
                        .orElseThrow(() -> new IllegalStateException("No non-mobile tokens found for user"));
            } else {
                // Nếu tất cả các token đều là mobile, xóa token có ngày hết hạn gần nhất
                tokenToDelete = userTokens.stream()
                        .min(Comparator.comparing(Token::getExpirationDate))
                        .orElseThrow(() -> new IllegalStateException("No tokens found for user"));
            }

            tokenRepository.delete(tokenToDelete);
        }

        long expirationInSeconds = expiration;
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expirationInSeconds);

        // Tạo mới một token cho người dùng
        Token newToken = Token.builder()
                .user(user)
                .token(token)
                .tokenType("Bearer")
                .expirationDate(expirationDateTime)
                .isMobile(isMobileDevice)
                .build();

        newToken.setRefreshToken(UUID.randomUUID().toString());
        newToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));
        tokenRepository.save(newToken);
        return newToken;
    }


    @Transactional
    @Override
    public Token refreshToken(String refreshToken, User user) {
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
        if(existingToken == null) {
            throw new DataNotFoundException("Refresh token does not exist");
        }
        if(existingToken.getRefreshExpirationDate().compareTo(LocalDateTime.now()) < 0){
            tokenRepository.delete(existingToken);
            throw new ExpiredTokenException("Refresh token is expired");
        }
        String token = jwtTokenUtil.generateToken(user);
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);
        existingToken.setExpirationDate(expirationDateTime);
        existingToken.setToken(token);
        existingToken.setRefreshToken(UUID.randomUUID().toString());
        existingToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));
        return existingToken;
    }
}
