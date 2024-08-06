package com.example.appdictionaryghtk.dtos;

import com.example.appdictionaryghtk.dtos.response.role.RoleResponse;
import com.example.appdictionaryghtk.dtos.response.user.UserResponse;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.util.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String email;
    private String fullname;
    private String dateOfBirth;
    private String avatar;
    private Gender gender;

    public static UserDTO toUser(User user) {
        return UserDTO.builder()
                .fullname(user.getFullname())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .build();
    }
}
