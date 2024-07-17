package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Words;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Words, Integer> {
}
