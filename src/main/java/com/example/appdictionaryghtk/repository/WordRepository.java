package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Words, Integer> {
    Optional<Words> findByName(String name);
    boolean existsByName(String name);

}
