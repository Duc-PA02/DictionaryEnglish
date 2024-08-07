package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Topic;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    boolean existsByName(String name);

    boolean existsById(int id);

    List<Topic> findByNameContaining(String name, Sort sort);

    List<Topic> findAll(Sort sort);
}
