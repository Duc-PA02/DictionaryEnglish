package com.example.appdictionaryghtk.service.searchHistory;

import com.example.appdictionaryghtk.entity.SearchHistory;


import java.util.List;

public interface ISearchHistoryService {
    // Lưu lịch sử tìm kiếm người dùng
    SearchHistory updateOrSaveSearchHistory(Integer userId, Integer wordId);
    //Lấy tất cả word_id sắp xếp theo total từ lớn đến bé theo User
    List<Integer> findWordIdsOrderByTotalDescByUserId(Integer userId);
}