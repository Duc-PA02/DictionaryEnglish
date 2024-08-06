package com.example.appdictionaryghtk.service.antonym;

import com.example.appdictionaryghtk.dtos.word_management.antonym.AntonymDTO;

public interface IAntonymService {
    AntonymDTO create(Integer wordId, AntonymDTO antonymDTO);
    void deleteByID(Integer id);
}
