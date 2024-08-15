package com.example.appdictionaryghtk.dtos.response.user;

import com.example.appdictionaryghtk.util.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class UserUpdateDTO {
    private UserStatus status;
    private Set<Integer> roleIds;
    private Set<Integer> permissionIds;
}
