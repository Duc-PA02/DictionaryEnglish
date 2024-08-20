package com.example.appdictionaryghtk.service.elasticsearch;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;
import com.example.appdictionaryghtk.repository.ElasticsearchWordsRepositoty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ElasticsearchWordsService implements IElasticsearchWordsService {

    private final ElasticsearchWordsRepositoty elasticsearchWordsRepositoty;

    @Override
    public List<WordDTO> searchByKeyword(String keyword){
        return elasticsearchWordsRepositoty.searchByKeyword(keyword);
    }

    @Override
    public List<WordDTO> searchByNameFuzzyOrPrefix(String value) {
        Set<WordDTO> results = new LinkedHashSet<>();
        String prefix = value;

        // Sử dụng Set để theo dõi các từ đã được thêm vào danh sách
        Set<Integer> seenIds = new HashSet<>();

        while (results.size() < 10 && prefix.length() > 0) {
            List<WordDTO> found = elasticsearchWordsRepositoty.findByNameFuzzyOrPrefix(value, prefix);

            for (WordDTO word : found) {
                if (results.size() >= 10) {
                    break;
                }
                // Chỉ thêm từ vào kết quả nếu nó chưa được thêm vào
                if (!seenIds.contains(word.getId())) {
                    results.add(word);
                    seenIds.add(word.getId());
                }
            }

            // Giảm dần độ dài prefix
            prefix = prefix.substring(0, prefix.length() - 1);
        }

        // Chuyển đổi Set thành List và trả về
        return new ArrayList<>(results);
    }
}