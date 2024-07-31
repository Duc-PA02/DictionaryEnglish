package com.example.appdictionaryghtk.controller.admin;

import com.example.appdictionaryghtk.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/admin/user")
@RequiredArgsConstructor
public class UserAdminController {
    private final IUserService userService;

}
