package com.example.appdictionaryghtk.dtos.response.user;

import com.example.appdictionaryghtk.dtos.response.permission.PermissionResponse;
import com.example.appdictionaryghtk.dtos.response.role.RoleResponse;
import com.example.appdictionaryghtk.util.Gender;
import com.example.appdictionaryghtk.util.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailResponse {
    private Integer id;
    private String username;
    private String email;
    private String fullname;
    private String dateOfBirth;
    private String avatar;
    private Gender gender;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<RoleResponse> roles;
    private Set<PermissionResponse> permissions;
}
