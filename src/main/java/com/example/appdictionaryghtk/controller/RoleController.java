package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.response.ResponseObject;
import com.example.appdictionaryghtk.service.role.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/role")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAll(){
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(roleService.getAll())
                .build());
    }
}
