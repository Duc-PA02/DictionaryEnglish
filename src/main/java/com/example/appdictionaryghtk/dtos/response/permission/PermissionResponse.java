package com.example.appdictionaryghtk.dtos.response.permission;

import lombok.Data;

@Data
public class PermissionResponse {
    private Integer id;
    private String name;
    private String method;
    private String path;
}
