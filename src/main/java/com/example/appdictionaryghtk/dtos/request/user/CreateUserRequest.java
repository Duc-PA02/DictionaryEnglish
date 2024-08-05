package com.example.appdictionaryghtk.dtos.request.user;

import com.example.appdictionaryghtk.util.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @Email(message = "Email Invalid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String fullname;
    @NotNull(message = "Gender is required")
    private Gender gender;
}