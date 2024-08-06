package com.example.appdictionaryghtk.dtos.response.word;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WordWithAntonymSynonymTypeResponse {
        private int id;
        private String name;
        private List<SynonymResponse> synonymsList;
        private List<AntonymResponse> antonymsList;
        private List<TypeResponse> typeList;
}
