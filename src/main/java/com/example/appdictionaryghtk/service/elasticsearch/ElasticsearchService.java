package com.example.appdictionaryghtk.service.elasticsearch;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.repository.ElasticsearchWordsRepositoty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ElasticsearchService {

    private final ElasticsearchWordsRepositoty elasticsearchWordsRepositoty;

    public void indexWordData(Word word) {
        WordDTO wordDto = WordDTO.fromEntity(word);
//        WordDTO wordDto = new WordDTO(2,"aaa");

        // Lưu tài liệu vào Elasticsearch
        elasticsearchWordsRepositoty.save(wordDto);
        System.out.println("Indexed document with ID: " + wordDto.getId());
    }
}