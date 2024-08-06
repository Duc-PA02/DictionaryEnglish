package com.example.appdictionaryghtk.controller.admin;

import com.example.appdictionaryghtk.dtos.response.ResponseObject;
import com.example.appdictionaryghtk.dtos.response.permission.PermissionRequest;
import com.example.appdictionaryghtk.dtos.response.permission.PermissionResponse;
import com.example.appdictionaryghtk.service.permission.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/admin/permission")
@RequiredArgsConstructor
public class PermissionAdminController {
    private final IPermissionService permissionService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllPermission(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<PermissionResponse> permissions = permissionService.getAllPermission(pageable);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(permissions)
                .build());
    }
    @PostMapping
    public ResponseEntity<ResponseObject> createPermission(@RequestBody PermissionRequest permissionRequest){
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(permissionService.createPermission(permissionRequest))
                .build());
    }
    @PutMapping("/{permissionId}")
    public ResponseEntity<ResponseObject> updatePermission(@PathVariable Integer permissionId, @RequestBody PermissionRequest permissionRequest){
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(permissionService.updatePermission(permissionId, permissionRequest))
                .build());
    }
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<ResponseObject> deletePermission(@PathVariable Integer permissionId){
        permissionService.deletePermission(permissionId);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .build());
    }
}
