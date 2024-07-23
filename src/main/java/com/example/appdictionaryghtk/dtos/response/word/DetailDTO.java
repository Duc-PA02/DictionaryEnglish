package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Data;

@Data
public class DetailDTO {

    private int type_id;
    private int definition_id;
    private int pronunciation_id;
    private String type;
    private String definition;
    private String example;
    private String audio;
    private String pronunciation;
    private String region;
}
