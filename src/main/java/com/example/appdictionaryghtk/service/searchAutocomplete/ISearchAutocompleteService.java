package com.example.appdictionaryghtk.service.searchAutocomplete;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;

import java.util.List;

public interface ISearchAutocompleteService {
    //Tìm kiếm theo tần suất người dùng toàn server
    List<WordDTO> searchByKeywordAndSortByTotalDesc(String keyword, Integer limit);

    //Tìm kiếm theo tần suất của từng người dùng (Lịch sử của người dùng)
    List<WordDTO> searchWordIdsOrderByTotalDescByUserId(Integer userId, String keyword, Integer limit);
}
