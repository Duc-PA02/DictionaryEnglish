package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {
    // Tìm kiếm SearchHistory theo user_id và word_id
    @Query(value = "SELECT * FROM search_history WHERE user_id = :userId AND word_id = :wordId", nativeQuery = true)
    Optional<SearchHistory> findByUserIdAndWordId(Integer userId, Integer wordId);

    //Lấy danh sách word_id theo user_id đã sắp xếp theo total giảm dần.
    @Query(value = "SELECT word_id FROM search_history WHERE user_id = :userId ORDER BY total DESC LIMIT %:limit%", nativeQuery = true)
    List<Integer> findWordIdsOrderByTotalDescByUserId(Integer userId, Integer limit);

    //Cộng tổng số lượt tìm kiếm của 1 từ
    @Query(value = "SELECT word_id, SUM(total) as total FROM search_history GROUP BY word_id", nativeQuery = true)
    List<Object[]> findTotalByWord();
}
