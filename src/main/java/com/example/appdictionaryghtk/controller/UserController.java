package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.UserDTO;
import com.example.appdictionaryghtk.dtos.request.user.ChangePasswordRequest;
import com.example.appdictionaryghtk.dtos.request.user.UpdateUserRequest;
import com.example.appdictionaryghtk.dtos.response.ResponseObject;
import com.example.appdictionaryghtk.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @GetMapping("/me")
    public ResponseEntity<ResponseObject> getMyInfo() {
        return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userService.getMyInfo())
                .build());
    }
    @PostMapping("/change-password")
    public ResponseEntity<ResponseObject> changePassword(
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest,
            @RequestHeader("Authorization") String token
    ) {
        userService.changePassword(token, changePasswordRequest);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Password changed successfully")
                .status(HttpStatus.OK)
                .build());
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseObject> updateUserInfo(
            @PathVariable Integer userId,
            @Valid @RequestBody UpdateUserRequest updateUserRequest
    ) {
        UserDTO updatedUser = userService.updateUserInfo(userId, updateUserRequest);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("User info updated successfully")
                .data(updatedUser)
                .status(HttpStatus.OK)
                .build());
    }
    @PutMapping("/{userId}/avatar")
    public ResponseEntity<ResponseObject> updateAvatar(@PathVariable Integer userId, @RequestParam MultipartFile avatar) throws IOException {
        UserDTO userDTO = userService.updateAvatar(userId, avatar);
        return ResponseEntity.ok().body(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Avatar updated successfully")
                .data(userDTO)
                .build());
    }
}
