package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    public ResponseEntity<?> getUserById(@RequestParam Integer id){
        return null;
    }
}
