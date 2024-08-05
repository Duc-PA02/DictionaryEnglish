package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TypeResponse {
    private int id;
    private String type;
    //private WordDTO word;
    private List<PronunciationResponse> pronunciationsList;
    private List<DefinitionResponse> definitionsList;
}
