package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Data;

@Data
public class PronunciationDTO {
    private int id;
    private String region;
    private String audio;
    private String pronunciation;
}
