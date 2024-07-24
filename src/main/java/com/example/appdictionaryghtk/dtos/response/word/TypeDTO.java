package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TypeDTO {
    private int id;
    private String type;
    //private WordDTO word;
    private List<PronunciationDTO> pronunciationsList;
    private List<DefinitionDTO> definitionsList;
}
