package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    boolean existsByName(String name);

    boolean existsById(int id);

    Page<Topic> findByNameContaining(String name, Pageable pageable);

    Page<Topic> findAll(Pageable pageable);
}
