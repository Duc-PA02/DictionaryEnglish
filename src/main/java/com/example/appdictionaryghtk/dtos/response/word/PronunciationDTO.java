package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PronunciationDTO {
    private int id;
    private String region;
    private String audio;
    private String pronunciation;
}
