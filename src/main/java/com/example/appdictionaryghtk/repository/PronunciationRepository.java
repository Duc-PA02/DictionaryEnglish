package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Pronunciations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PronunciationRepository extends JpaRepository<Pronunciations, Integer> {
    List<Pronunciations> findByTypeId(Integer typeID);
}
