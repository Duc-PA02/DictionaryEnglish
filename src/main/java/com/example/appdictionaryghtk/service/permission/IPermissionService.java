package com.example.appdictionaryghtk.service.permission;

import com.example.appdictionaryghtk.dtos.response.permission.PermissionResponse;
import com.example.appdictionaryghtk.entity.Permission;
import com.example.appdictionaryghtk.entity.User;

import java.util.List;
import java.util.Set;

public interface IPermissionService {
    Set<Permission> getPermissionsByUser(User user);
    List<PermissionResponse> getAllPermission();
}
