package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    public Word findByName(String name);

    public Boolean existsById(int id);

    public Boolean existsByName(String name);
}
