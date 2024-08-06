package com.example.appdictionaryghtk.dtos.request.user;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String code;
    private String password;
}

