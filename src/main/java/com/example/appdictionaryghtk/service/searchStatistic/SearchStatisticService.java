package com.example.appdictionaryghtk.service.searchStatistic;

import com.example.appdictionaryghtk.entity.SearchStatistic;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.repository.SearchHistoryRepository;
import com.example.appdictionaryghtk.repository.SearchStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchStatisticService implements ISearchStatisticService{

    private final SearchStatisticRepository searchStatisticRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    @Override
    public SearchStatistic save(SearchStatistic searchStatistic) {
        return null;
    }

    @Override
    public List<Integer> findWordIdsOrderByTotalDesc(Integer limit) {
        return searchStatisticRepository.findWordIdsOrderByTotalDesc(limit);
    }

    @Override
    public List<SearchStatistic> updateSearchStatistics() {
        List<Object[]> results = searchHistoryRepository.findTotalByWord();
        List<SearchStatistic> updatedStatistics = new ArrayList<>();

        for (Object[] result : results) {
            Integer wordId = (Integer) result[0];
            BigDecimal totalBigDecimal = (BigDecimal) result[1];
            Integer total = totalBigDecimal.intValue();

            // Kiểm tra xem đã có SearchStatistic cho wordId này chưa
            SearchStatistic searchStatistic = searchStatisticRepository.findByWordId(wordId)
                    .orElseGet(() -> {
                        // Nếu chưa có, tạo mới SearchStatistic và gán thông tin từ Words
                        SearchStatistic newStatistic = new SearchStatistic();
                        Word word = new Word();
                        word.setId(wordId);
                        newStatistic.setWord(word);
                        return newStatistic;
                    });

            // Đặt lại giá trị total cho SearchStatistic
            searchStatistic.setTotal(total);

            // Lưu hoặc cập nhật SearchStatistic vào repository
            searchStatisticRepository.save(searchStatistic);

            // Thêm vào danh sách kết quả cập nhật
            updatedStatistics.add(searchStatistic);
        }

        return updatedStatistics;
    }
}
