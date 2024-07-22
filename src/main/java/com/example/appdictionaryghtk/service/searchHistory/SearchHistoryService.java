package com.example.appdictionaryghtk.service.searchHistory;

import com.example.appdictionaryghtk.entity.SearchHistory;
import com.example.appdictionaryghtk.entity.Users;
import com.example.appdictionaryghtk.entity.Words;
import com.example.appdictionaryghtk.repository.SearchHistoryRepository;
import com.example.appdictionaryghtk.repository.UserRepository;
import com.example.appdictionaryghtk.repository.WordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SearchHistoryService implements ISearchHistoryService{

    private final SearchHistoryRepository searchHistoryRepository;
    private final UserRepository userRepository;
    private final WordsRepository wordsRepository;

    @Override
    public SearchHistory updateOrSaveSearchHistory(Integer userId, Integer wordId) {
        // Tìm kiếm SearchHistory theo user_id và word_id
        Optional<SearchHistory> optionalSearchHistory = searchHistoryRepository.findByUserIdAndWordId(userId, wordId);

        SearchHistory searchHistory;

        if (optionalSearchHistory.isPresent()) {
            // Nếu đã tồn tại, cập nhật updateAt và tăng total
            searchHistory = optionalSearchHistory.get();
            searchHistory.setUpdateAt(LocalDateTime.now());
            searchHistory.setTotal(searchHistory.getTotal() + 1);
        } else {
            // Nếu chưa tồn tại, thêm mới SearchHistory với total = 1
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            Words word = wordsRepository.findById(wordId)
                    .orElseThrow(() -> new RuntimeException("Word not found with id: " + wordId));

            searchHistory = new SearchHistory();
            searchHistory.setUser(user);
            searchHistory.setWord(word);
            searchHistory.setUpdateAt(LocalDateTime.now());
            searchHistory.setTotal(1);
        }
        return searchHistoryRepository.save(searchHistory);
    }

    @Override
    public List<Integer> findWordIdsOrderByTotalDescByUserId(Integer userId) {
        return searchHistoryRepository.findWordIdsOrderByTotalDescByUserId(userId);
    }
}
