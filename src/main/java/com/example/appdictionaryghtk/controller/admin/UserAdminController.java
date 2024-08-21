package com.example.appdictionaryghtk.controller.admin;

import com.example.appdictionaryghtk.dtos.response.ResponseObject;
import com.example.appdictionaryghtk.dtos.response.user.UserDetailResponse;
import com.example.appdictionaryghtk.dtos.response.user.UserResponse;
import com.example.appdictionaryghtk.dtos.response.user.UserUpdateDTO;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/admin/user")
@RequiredArgsConstructor
public class UserAdminController {
    private final IUserService userService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAllUser(
            @RequestParam(defaultValue = "username") String sort,
            @RequestParam(defaultValue = "asc") String direction,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ){
        Page<UserResponse> users = userService.getAllUser(pageable, sort, direction);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(users)
                .build());
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable Integer userId){
        UserDetailResponse userDetail = userService.getUserById(userId);
        return ResponseEntity.ok(ResponseObject.builder()
                        .status(HttpStatus.OK)
                        .data(userDetail)
                .build());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable Integer userId, @RequestBody UserUpdateDTO userUpdateDTO){
        UserDetailResponse userDetail = userService.updateUser(userId, userUpdateDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .data(userDetail)
                .build());
    }
}
