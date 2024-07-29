package com.example.appdictionaryghtk.dtos.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String fullname;
    private String email;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    private String gender;
}
