package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.TopicWord;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicWordRepository extends JpaRepository<TopicWord, Integer> {
    Boolean existsByWordIdAndTopicId(int wid, int tid);

    Boolean existsByTopicIdAndWordNameContaining(int tid, String name);

    List<TopicWord> findByTopicId(int id, Sort sort);

    List<TopicWord> findByTopicIdAndWordNameContaining(int tid, String name, Sort sort);

}
