package com.example.appdictionaryghtk.service.word_management;

import com.example.appdictionaryghtk.dtos.word_management.word.WordDTO;
import com.example.appdictionaryghtk.dtos.word_management.word.WordDetail;

import java.util.List;

public interface IWordService {
    WordDetail findByID(Integer wordID) ;
    WordDetail create(WordDetail request);
    WordDetail update(Integer wordID, WordDetail wordDetail);
    void deleteByID(Integer id);
    List<WordDTO> findByName(String name);
    List<WordDTO> getWordsAfter(Integer lastId, int limit);

    List<WordDTO> getWordsBefore(Integer firstId, int limit);
}
