package com.example.appdictionaryghtk.service.elasticsearch;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;
import com.example.appdictionaryghtk.repository.ElasticsearchWordsRepositoty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ElasticsearchWordsService implements IElasticsearchWordsService {

    private final ElasticsearchWordsRepositoty elasticsearchWordsRepositoty;

    @Override
    public List<WordDTO> searchByKeyword(String keyword){
        return elasticsearchWordsRepositoty.searchByKeyword(keyword);
    }
}