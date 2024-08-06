package com.example.appdictionaryghtk.dtos.response.permission;

import lombok.Getter;

@Getter
public class PermissionRequest {
    private String name;
    private String method;
    private String path;
}
