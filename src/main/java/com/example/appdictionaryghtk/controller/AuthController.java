package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.request.user.CreateUserRequest;
import com.example.appdictionaryghtk.dtos.request.user.ForgotPasswordRequest;
import com.example.appdictionaryghtk.dtos.request.user.LoginRequest;
import com.example.appdictionaryghtk.dtos.request.user.ResetPasswordRequest;
import com.example.appdictionaryghtk.dtos.response.ResponseObject;
import com.example.appdictionaryghtk.dtos.response.user.LoginResponse;
import com.example.appdictionaryghtk.dtos.response.user.UserResponse;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.service.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@Valid @RequestBody CreateUserRequest userDTO) {
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .data(UserResponse.fromUser(user))
                .message("Register successfuly")
                .build());
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletRequest request
    ) {
        String userAgent = request.getHeader("User-Agent");
        LoginResponse loginResponse = userService.login(loginRequest, userAgent);

        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Login successfully")
                .data(loginResponse)
                .status(HttpStatus.OK)
                .build());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseObject> forgotPassWord(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String confirmEmail = userService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .message(confirmEmail)
                        .status(HttpStatus.OK)
                .build());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseObject> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        String message = userService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message(message)
                .status(HttpStatus.OK)
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseObject> logout(
            @RequestHeader("Authorization") String token
    ) {
        userService.logout(token);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Logout successfully")
                .status(HttpStatus.OK)
                .build());
    }

}
