package com.example.appdictionaryghtk.service.searchStatistic;

import com.example.appdictionaryghtk.entity.SearchStatistic;

import java.util.List;

public interface ISearchStatisticService {
    SearchStatistic save(SearchStatistic searchStatistic);

    //Lấy tất cả word_id sắp xếp theo total từ lớn đến bé
    List<Integer> findWordIdsOrderByTotalDesc();

    //Cộng tổng lượt tìm kiếm vào SearchStatistic
    List<SearchStatistic> updateSearchStatistics();
}
