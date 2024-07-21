package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Data;

@Data
public class DefinitionDTO {
    private int id;
    private String definition;
    private String examples;
    private int type_word_id;
}
