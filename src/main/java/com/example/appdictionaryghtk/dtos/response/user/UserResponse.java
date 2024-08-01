package com.example.appdictionaryghtk.dtos.response.user;

import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.util.Gender;
import com.example.appdictionaryghtk.util.UserStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private String fullname;
    private String dateOfBirth;
    private String avatar;
    private Gender gender;
    private UserStatus status;
    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .status(user.getStatus())
                .build();
    }
}
