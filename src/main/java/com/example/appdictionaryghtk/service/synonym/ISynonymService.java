package com.example.appdictionaryghtk.service.synonym;

import com.example.appdictionaryghtk.dtos.response.word.SynonymDTO;

public interface ISynonymService {
    SynonymDTO create(Integer wordId, SynonymDTO synonymDTO);
    void deleteByID(Integer id);
}
