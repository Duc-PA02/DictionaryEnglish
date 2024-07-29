package com.example.appdictionaryghtk.dtos.request.user;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateAvatarRequest {
    private MultipartFile avatar;
}
