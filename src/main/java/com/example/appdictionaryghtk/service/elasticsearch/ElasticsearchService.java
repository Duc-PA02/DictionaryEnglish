package com.example.appdictionaryghtk.service.elasticsearch;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.repository.ElasticsearchWordsRepositoty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ElasticsearchService {

    private final ElasticsearchWordsRepositoty elasticsearchWordsRepositoty;

    public void indexWordData(Word word) {
        try {
            WordDTO wordDto = WordDTO.fromEntity(word);
            elasticsearchWordsRepositoty.save(wordDto);
            System.out.println("Indexed document with ID: " + wordDto.getId());
        }catch (Exception e){
            log.info("Error indexing word data: {}", e);
            e.printStackTrace();
        }

    }
}