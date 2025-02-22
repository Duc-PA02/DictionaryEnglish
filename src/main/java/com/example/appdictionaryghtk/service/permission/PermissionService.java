package com.example.appdictionaryghtk.service.permission;

import com.example.appdictionaryghtk.dtos.response.permission.PermissionRequest;
import com.example.appdictionaryghtk.dtos.response.permission.PermissionResponse;
import com.example.appdictionaryghtk.dtos.response.role.RoleResponse;
import com.example.appdictionaryghtk.entity.Permission;
import com.example.appdictionaryghtk.entity.Role;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.repository.PermissionRepository;
import com.example.appdictionaryghtk.repository.RoleRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService{
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
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
    public Page<PermissionResponse> getAllPermission(Pageable pageable) {
        Page<Permission> permissionList = permissionRepository.findAll(pageable);
        return permissionList.map(permission -> modelMapper.map(permission, PermissionResponse.class));
    }

    @Override
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = Permission.builder()
                .name(permissionRequest.getName())
                .method(permissionRequest.getMethod())
                .path(permissionRequest.getPath())
                .build();
        permissionRepository.save(permission);
        PermissionResponse permissionResponse = modelMapper.map(permission, PermissionResponse.class);
        return permissionResponse;
    }

    @Override
    public PermissionResponse updatePermission(Integer permissionId, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(permissionId).orElse(null);
        if (permission == null){
            throw new DataNotFoundException("permission not found");
        }
        permission.setName(permissionRequest.getName());
        permission.setMethod(permissionRequest.getMethod());
        permission.setPath(permissionRequest.getPath());
        permissionRepository.save(permission);
        PermissionResponse permissionResponse = modelMapper.map(permission, PermissionResponse.class);
        return permissionResponse;
    }

    @Override
    public void deletePermission(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission with id " + id + " not found"));

        for (User user : userRepository.findAll()) {
            user.getPermissions().remove(permission);
            userRepository.save(user);
        }
        for (Role role : roleRepository.findAll()) {
            role.getPermissions().remove(permission);
            roleRepository.save(role);
        }

        permissionRepository.deleteById(id);
    }
}