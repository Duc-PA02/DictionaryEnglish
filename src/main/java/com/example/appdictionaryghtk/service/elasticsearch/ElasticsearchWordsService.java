package com.example.appdictionaryghtk.service.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.appdictionaryghtk.dtos.elasticsearch.WordsDTO;
import com.example.appdictionaryghtk.repository.ElasticsearchWordsRepositoty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ElasticsearchWordsService implements IElasticsearchWordsService {

    private final ElasticsearchClient esClient;

    private final ElasticsearchWordsRepositoty elasticsearchWordsRepositoty;

    @Override
    public List<WordsDTO> searchByKeyword(String keyword) {
        return elasticsearchWordsRepositoty.searchByKeyword(keyword);
    }
}