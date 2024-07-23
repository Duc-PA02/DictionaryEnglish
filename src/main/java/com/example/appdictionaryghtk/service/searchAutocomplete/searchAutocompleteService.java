package com.example.appdictionaryghtk.service.searchAutocomplete;


import com.example.appdictionaryghtk.dtos.elasticsearch.WordsDTO;
import com.example.appdictionaryghtk.service.elasticsearch.IElasticsearchWordsService;
import com.example.appdictionaryghtk.service.searchHistory.ISearchHistoryService;
import com.example.appdictionaryghtk.service.searchStatistic.ISearchStatisticService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class searchAutocompleteService implements ISearchAutocompleteService {

    private final ISearchStatisticService searchStatisticService;

    private final ISearchHistoryService searchHistoryService;

    private final IElasticsearchWordsService elasticsearchWordsService;

    @Override
    public List<WordsDTO> searchByKeywordAndSortByTotalDesc(String keyword, Integer limit) {
        // Bước 1: Tìm kiếm từ khoá prefix trong Elasticsearch
        List<WordsDTO> words = elasticsearchWordsService.searchByKeyword(keyword);

        // Bước 2: Lấy danh sách word_id đã sắp xếp theo total từ MySQL
        List<Integer> sortedWordIds = searchStatisticService.findWordIdsOrderByTotalDesc(limit);

        // Bước 3: Tạo một Map để lưu trữ thứ tự sắp xếp dựa trên word_id từ MySQL
        Map<Integer, Integer> wordIdToOrderMap = new HashMap<>();
        for (int i = 0; i < sortedWordIds.size(); i++) {
            wordIdToOrderMap.put(sortedWordIds.get(i), i);
        }

        // Bước 4: Sắp xếp danh sách words theo thứ tự dựa trên Map, giữ nguyên thứ tự cho từ không có trong Map
        List<WordsDTO> sortedWords = words.stream()
                .sorted(Comparator.comparingInt(word -> wordIdToOrderMap.getOrDefault(word.getId(), Integer.MAX_VALUE)))
                .collect(Collectors.toList());

        if (sortedWords.size() > 7) {
            sortedWords = sortedWords.subList(0, 7);
        }
        return sortedWords;
    }

    @Override
    public List<WordsDTO> searchWordIdsOrderByTotalDescByUserId(Integer userId, String keyword, Integer limit) {
        // Bước 1: Tìm kiếm từ khoá prefix trong Elasticsearch
        List<WordsDTO> words = elasticsearchWordsService.searchByKeyword(keyword);

        // Bước 2: Lấy danh sách word_id đã sắp xếp theo total từ MySQL
        List<Integer> sortedWordIds = searchHistoryService.findWordIdsOrderByTotalDescByUserId(userId, limit);

        // Bước 3: Tạo một Map để lưu trữ thứ tự sắp xếp dựa trên word_id từ MySQL
        Map<Integer, Integer> wordIdToOrderMap = new HashMap<>();
        for (int i = 0; i < sortedWordIds.size(); i++) {
            wordIdToOrderMap.put(sortedWordIds.get(i), i);
        }

        // Bước 4: Lọc và sắp xếp danh sách words theo thứ tự dựa trên Map
        List<WordsDTO> sortedWords = words.stream()
                .filter(word -> wordIdToOrderMap.containsKey(word.getId()))  // Chỉ giữ lại từ có trong Map
                .sorted(Comparator.comparingInt(word -> wordIdToOrderMap.get(word.getId())))
                .collect(Collectors.toList());

        if (sortedWords.size() > 3) {
            sortedWords = sortedWords.subList(0, 3);
        }

        return sortedWords;
    }
}
