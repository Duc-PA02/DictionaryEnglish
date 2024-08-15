package com.example.appdictionaryghtk.dtos.response.role;

import com.example.appdictionaryghtk.dtos.response.permission.PermissionResponse;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
    private Integer id;
    private String role;
    private Set<PermissionResponse> permissions;
}
