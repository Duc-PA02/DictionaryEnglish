package com.example.appdictionaryghtk.dtos.word_management.antonym;

import com.example.appdictionaryghtk.dtos.word_management.word.WordDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AntonymDTO {
    Integer id;
    WordDTO antonym;
}
