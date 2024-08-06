package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Synonyms;
import com.example.appdictionaryghtk.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynonymRepository extends JpaRepository<Synonyms, Integer> {
    boolean existsBySynonymAndWord(Word synonym, Word word);
}
