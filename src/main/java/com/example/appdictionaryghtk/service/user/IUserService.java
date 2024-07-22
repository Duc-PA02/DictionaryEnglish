package com.example.appdictionaryghtk.service.user;

import com.example.appdictionaryghtk.dtos.UserDTO;
import com.example.appdictionaryghtk.dtos.request.user.ForgotPasswordRequest;
import com.example.appdictionaryghtk.dtos.request.user.LoginRequest;
import com.example.appdictionaryghtk.entity.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(LoginRequest loginRequest) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    String forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws Exception;
    User getUserById(Integer id) throws Exception;
}
