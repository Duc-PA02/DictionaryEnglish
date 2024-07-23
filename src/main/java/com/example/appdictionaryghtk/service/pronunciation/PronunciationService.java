package com.example.appdictionaryghtk.service.pronunciation;

import com.example.appdictionaryghtk.dtos.word_management.pronunciation.PronunciationDTO;

public interface IPronunciationService {
    PronunciationDTO findByID(Integer id);
    PronunciationDTO create(Integer typeID, PronunciationDTO pronunciationDTO);
    PronunciationDTO update(Integer id, PronunciationDTO pronunciationDTO);
    void deleteByID(Integer id);
}
