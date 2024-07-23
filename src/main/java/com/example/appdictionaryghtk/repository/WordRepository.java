package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
  
    Optional<Word> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT w from Word w where w.name= ?1")
    public Word findWordByName(String name);
}
