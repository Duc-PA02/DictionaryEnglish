package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.SearchStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchStatisticRepository extends JpaRepository<SearchStatistic, Integer> {
    //Lấy danh sách word_id đã sắp xếp theo total giảm dần.
    @Query(value = "SELECT word_id FROM search_statistic ORDER BY total DESC", nativeQuery = true)
    List<Integer> findWordIdsOrderByTotalDesc();

    //Lấy searchStatistic theo wordId
    Optional<SearchStatistic> findByWordId(Integer wordId);
}
