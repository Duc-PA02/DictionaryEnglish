package com.example.appdictionaryghtk.service.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;
import com.example.appdictionaryghtk.entity.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ElasticsearchService {

    private final ElasticsearchClient esClient;

    public void indexWordData(Word word) throws IOException {
        WordDTO wordDto = WordDTO.fromEntity(word);
        IndexRequest<WordDTO> request = IndexRequest.of(i -> i
                .index("word")
                .id(wordDto.getId().toString())
                .document(wordDto)
        );
        System.out.println("Indexed with version " + wordDto.getName());
        IndexResponse response = esClient.index(request);
    }
}