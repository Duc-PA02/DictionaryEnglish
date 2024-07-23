package com.example.appdictionaryghtk.dtos.word_management.word;

import com.example.appdictionaryghtk.dtos.word_management.type.TypeDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class WordDetail {
    Integer id;
    String name;
    List<TypeDTO> typeList;
}
