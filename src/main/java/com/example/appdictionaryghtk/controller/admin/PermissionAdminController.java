package com.example.appdictionaryghtk.controller.admin;

import com.example.appdictionaryghtk.dtos.response.ResponseObject;
import com.example.appdictionaryghtk.service.permission.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/admin/permission")
@RequiredArgsConstructor
public class PermissionAdminController {
    private final IPermissionService permissionService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllPermission(){
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(permissionService.getAllPermission())
                .build());
    }
}
