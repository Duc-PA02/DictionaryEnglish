package com.example.appdictionaryghtk.service.permission;

import com.example.appdictionaryghtk.dtos.response.permission.PermissionResponse;
import com.example.appdictionaryghtk.dtos.response.role.RoleResponse;
import com.example.appdictionaryghtk.entity.Permission;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService{
    private final PermissionRepository permissionRepository;
    private final ModelMapper modelMapper;
    @Override
    public Set<Permission> getPermissionsByUser(User user) {
        Set<Permission> rolePermissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toSet());

        Set<Permission> directPermissions = user.getPermissions();

        return Stream.concat(rolePermissions.stream(), directPermissions.stream())
                .collect(Collectors.toSet());
    }

    @Override
    public List<PermissionResponse> getAllPermission() {
        List<Permission> permissionList = permissionRepository.findAll();
        return permissionList.stream()
                .map(permission -> modelMapper.map(permission, PermissionResponse.class))
                .collect(Collectors.toList());
    }
}
