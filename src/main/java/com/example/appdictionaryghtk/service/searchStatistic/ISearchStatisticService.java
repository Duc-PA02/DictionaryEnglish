package com.example.appdictionaryghtk.service.searchStatistic;

import com.example.appdictionaryghtk.entity.SearchStatistic;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

public interface ISearchStatisticService {
    SearchStatistic save(SearchStatistic searchStatistic);

    //Lấy tất cả word_id sắp xếp theo total từ lớn đến bé
    List<Integer> findWordIdsOrderByTotalDesc(@RequestParam("limit") Integer limit);

    //Cộng tổng lượt tìm kiếm vào SearchStatistic
    List<SearchStatistic> updateSearchStatistics();
}
