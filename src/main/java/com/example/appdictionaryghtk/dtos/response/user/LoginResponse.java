package com.example.appdictionaryghtk.dtos.response.user;

import com.example.appdictionaryghtk.dtos.response.role.RoleResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

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
    private List<RoleResponse> roles;
}
