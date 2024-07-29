package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Data;

import java.util.List;

@Data
public class TypeDTO {
    private int id;
    private String type;
    //private WordDTO word;
    private List<PronunciationDTO> pronunciationsList;
    private List<DefinitionDTO> definitionsList;
}
