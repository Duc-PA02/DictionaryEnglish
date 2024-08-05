package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.TopicWord;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicWordRepository extends JpaRepository<TopicWord, Integer> {
    Boolean existsByWordIdAndTopicId(int wid, int tid);

    Boolean existsByTopicIdAndWordName(int tid, String name);

    List<TopicWord> findByTopicId(int id, Sort sort);

    List<TopicWord> findByTopicIdAndWordName(int tid, String name);
}
