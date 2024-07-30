package com.example.appdictionaryghtk.service.elasticsearch;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;

import java.util.List;

public interface IElasticsearchWordsService {
    //Tìm kiếm từ theo tiền tố
    List<WordDTO> searchByKeyword(String keyword);
}
