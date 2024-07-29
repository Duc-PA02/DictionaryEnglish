package com.example.appdictionaryghtk.dtos.response.word;

import com.example.appdictionaryghtk.entity.Type;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
public class WordWithAntonymSynonymTypeDTO {
        private int id;
        private String name;
        private List<SynonymDTO> synonymsList;
        private List<AntonymDTO> antonymsList;
        private List<TypeDTO> typeList;
}
