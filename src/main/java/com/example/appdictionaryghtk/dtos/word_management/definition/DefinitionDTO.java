package com.example.appdictionaryghtk.dtos.word_management.definition;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class DefinitionDTO {
    Integer id;

    String definition;

    String examples;

}

