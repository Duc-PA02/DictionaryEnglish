package com.example.appdictionaryghtk.service.word;

import com.example.appdictionaryghtk.dtos.word_management.word.WordDetail;
import org.springframework.data.domain.Page;

public interface IWordService {
    Page<WordDetail> findAll(Integer pageNumber, Integer pageSize, String sort);
    WordDetail findByID(Integer wordID) ;
    WordDetail create(WordDetail request);
    WordDetail update(Integer wordID, WordDetail wordDetail);
    void deleteByID(Integer id);
}
