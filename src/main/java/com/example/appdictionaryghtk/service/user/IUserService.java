package com.example.appdictionaryghtk.service.user;

import com.example.appdictionaryghtk.dtos.UserDTO;
import com.example.appdictionaryghtk.dtos.request.user.*;
import com.example.appdictionaryghtk.dtos.response.user.LoginResponse;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.exceptions.DataNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService {
    User createUser(CreateUserRequest createUserRequest);
    LoginResponse login(LoginRequest loginRequest, String userAgent);
    User getUserDetailsFromToken(String token);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    UserDTO getMyInfo() ;
    void logout(String token);
    void changePassword(String token, ChangePasswordRequest changePasswordRequest);
    UserDTO updateUserInfo(Integer userId, UpdateUserRequest updateUserRequest);
    UserDTO updateAvatar(Integer userId, MultipartFile avatarFile) throws IOException;
}
