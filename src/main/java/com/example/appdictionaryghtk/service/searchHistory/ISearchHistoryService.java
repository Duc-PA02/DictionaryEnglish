package com.example.appdictionaryghtk.service.searchHistory;

import com.example.appdictionaryghtk.entity.SearchHistory;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ISearchHistoryService {
    // Lưu lịch sử tìm kiếm người dùng
    SearchHistory updateOrSaveSearchHistory(Integer userId, Integer wordId);
    //Lấy tất cả word_id sắp xếp theo total từ lớn đến bé theo User
    List<Integer> findWordIdsOrderByTotalDescByUserId(@Param("userId") Integer userId);
}