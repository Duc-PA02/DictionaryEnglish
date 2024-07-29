package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsRepository extends JpaRepository<Word,Integer> {
}
