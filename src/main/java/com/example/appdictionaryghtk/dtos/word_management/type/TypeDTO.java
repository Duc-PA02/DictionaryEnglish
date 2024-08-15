package com.example.appdictionaryghtk.dtos.word_management.type;

import com.example.appdictionaryghtk.dtos.word_management.definition.DefinitionDTO;
import com.example.appdictionaryghtk.dtos.word_management.pronunciation.PronunciationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeDTO {
    Integer id;
    String type;
    List<PronunciationDTO> pronunciationsList;
    List<DefinitionDTO> definitionsList;
}
