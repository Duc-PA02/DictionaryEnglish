package com.example.appdictionaryghtk.service.permission;

import com.example.appdictionaryghtk.dtos.response.permission.PermissionRequest;
import com.example.appdictionaryghtk.dtos.response.permission.PermissionResponse;
import com.example.appdictionaryghtk.entity.Permission;
import com.example.appdictionaryghtk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface IPermissionService {
    Set<Permission> getPermissionsByUser(User user);
    Page<PermissionResponse> getAllPermission(Pageable pageable);
    PermissionResponse createPermission(PermissionRequest permissionRequest);
    PermissionResponse updatePermission(Integer permissionId, PermissionRequest permissionRequest);
    void deletePermission(Integer id);
    List<PermissionResponse> getListPermission();
}
