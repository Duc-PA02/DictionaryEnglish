package com.example.appdictionaryghtk.service.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.appdictionaryghtk.dtos.elasticsearch.WordsDTO;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.repository.ElasticsearchWordsRepositoty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ElasticsearchService {

    private final ElasticsearchClient esClient;

    public void indexWordData(Word word) throws IOException {
        WordsDTO wordDto = WordsDTO.fromEntity(word);
        IndexRequest<WordsDTO> request = IndexRequest.of(i -> i
                .index("words")
                .id(wordDto.getId().toString())
                .document(wordDto)
        );

        IndexResponse response = esClient.index(request);
//        System.out.println("Indexed with version " + response.version());
    }
}