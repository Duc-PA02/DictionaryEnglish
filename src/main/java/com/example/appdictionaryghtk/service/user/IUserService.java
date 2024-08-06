package com.example.appdictionaryghtk.service.user;

import com.example.appdictionaryghtk.dtos.UserDTO;
import com.example.appdictionaryghtk.dtos.request.user.*;
import com.example.appdictionaryghtk.dtos.response.user.LoginResponse;
import com.example.appdictionaryghtk.dtos.response.user.UserResponse;
import com.example.appdictionaryghtk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(LoginRequest loginRequest) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception;

    //phuong add
    User getUserById(int id) throws Exception;
    //end

    User createUser(CreateUserRequest createUserRequest);
    LoginResponse login(LoginRequest loginRequest, String userAgent);
    User getUserDetailsFromToken(String token);
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
    UserDTO getMyInfo() ;
    void logout(String token);
    void changePassword(String token, ChangePasswordRequest changePasswordRequest);
    UserDTO updateUserInfo(Integer userId, UpdateUserRequest updateUserRequest);
    UserDTO updateAvatar(Integer userId, MultipartFile avatarFile) throws IOException;
    Page<UserResponse> getAllUser(Pageable pageable, String sort, String direction);
}
