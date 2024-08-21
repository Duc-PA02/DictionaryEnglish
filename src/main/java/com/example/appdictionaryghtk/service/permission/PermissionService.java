package com.example.appdictionaryghtk.service.permission;

import com.example.appdictionaryghtk.dtos.response.permission.PermissionRequest;
import com.example.appdictionaryghtk.dtos.response.permission.PermissionResponse;
import com.example.appdictionaryghtk.entity.Permission;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import com.example.appdictionaryghtk.exceptions.ResourceNotFoundException;
import com.example.appdictionaryghtk.repository.PermissionRepository;
import com.example.appdictionaryghtk.repository.RoleRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Transactional
    public void deletePermission(Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission with id " + id + " not found"));

        boolean isPermissionUsedByUser = userRepository.findAll().stream()
                .anyMatch(user -> user.getPermissions().contains(permission));
        if (isPermissionUsedByUser) {
            throw new IllegalStateException("Cannot delete permission as it is assigned to one or more users.");
        }
        boolean isPermissionUsedByRole = roleRepository.findAll().stream()
                .anyMatch(role -> role.getPermissions().contains(permission));
        if (isPermissionUsedByRole) {
            throw new IllegalStateException("Cannot delete permission as it is assigned to one or more roles.");
        }
        permissionRepository.deleteById(id);
    }

    @Override
    public List<PermissionResponse> getListPermission() {
        List<Permission> list = permissionRepository.findAll();
        return list.stream()
                .map(permission -> modelMapper.map(permission, PermissionResponse.class))
                .collect(Collectors.toList());
    }
}