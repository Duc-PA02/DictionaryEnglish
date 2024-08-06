package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DefinitionResponse {
    private int id;
    private String definition;
    private String examples;
    private int type_word_id;
}
