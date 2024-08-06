package com.example.appdictionaryghtk.dtos.word_management.synonym;

import com.example.appdictionaryghtk.dtos.word_management.word.WordDTO;
import lombok.Data;

@Data
public class SynonymDTO {
    Integer id;
    WordDTO synonym;
}
