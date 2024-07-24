package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DefinitionDTO {
    private int id;
    private String definition;
    private String examples;
    private int type_word_id;
}
