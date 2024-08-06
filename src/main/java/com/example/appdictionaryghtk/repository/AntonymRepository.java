package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Antonyms;
import com.example.appdictionaryghtk.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AntonymRepository extends JpaRepository<Antonyms, Integer> {
    boolean existsByAntonymAndWord(Word antonym, Word word);
//    @Query(value = "SELECT a.id as id, w.id as word_antonyms_id, w.name FROM antonyms a" +
//            "  INNER JOIN word w" +
//            "  ON a.word_id = w.id OR a.word_antonyms_id = w.id" +
//            "  WHERE w.id = :wordID"
//            , nativeQuery = true)
//    List<Antonyms> findAntonymByWordID(@Param("wordID") Integer wordID);
//    @Query(value = "SELECT a.id as id, a.word_id, a.word_antonyms_id, w.name FROM antonyms a " +
//            "INNER JOIN word w ON a.word_id = w.id OR a.word_antonyms_id = w.id " +
//            "WHERE a.word_id = :wordID OR a.word_antonyms_id = :wordID", nativeQuery = true)
//    List<AntonymDTO> findAntonymByWordID(@Param("wordID") Integer wordID);
}
