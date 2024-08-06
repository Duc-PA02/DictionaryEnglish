package com.example.appdictionaryghtk.service.synonym;

import com.example.appdictionaryghtk.dtos.word_management.synonym.SynonymDTO;

public interface ISynonymService {
    SynonymDTO create(Integer wordId, SynonymDTO synonymDTO);
    void deleteByID(Integer id);
}
