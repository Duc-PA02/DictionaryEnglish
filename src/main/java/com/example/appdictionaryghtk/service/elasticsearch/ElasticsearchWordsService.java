package com.example.appdictionaryghtk.service.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;
import com.example.appdictionaryghtk.repository.ElasticsearchWordsRepositoty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ElasticsearchWordsService implements IElasticsearchWordsService {

    private final ElasticsearchClient esClient;

    private final ElasticsearchWordsRepositoty elasticsearchWordsRepositoty;

    @Override
    public List<WordDTO> searchByKeyword(String keyword) {
        return elasticsearchWordsRepositoty.searchByKeyword(keyword);
    }
}