package com.example.appdictionaryghtk.service.elasticsearch;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordsDTO;

import java.util.List;

public interface IElasticsearchWordsService {
    //Tìm kiếm từ theo tiền tố
    List<WordsDTO> searchByKeyword(String keyword);
}
