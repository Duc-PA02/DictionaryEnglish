package com.example.appdictionaryghtk.dtos.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String tokenType = "Bearer";
}
