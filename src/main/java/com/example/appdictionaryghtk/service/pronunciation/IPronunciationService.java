package com.example.appdictionaryghtk.service.pronunciation;

import com.example.appdictionaryghtk.dtos.word_management.pronunciation.PronunciationDTO;

import java.util.List;

public interface IPronunciationService {
    PronunciationDTO findByID(Integer id);
    PronunciationDTO create(Integer typeID, PronunciationDTO pronunciationDTO);
    PronunciationDTO update(Integer id, PronunciationDTO pronunciationDTO);
//    List<PronunciationDTO> findAll();
    void deleteByID(Integer id);
    List<PronunciationDTO> findByTypeId(Integer typeID);
}
