package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    @Query(value = "SELECT COUNT(*) FROM word", nativeQuery = true)
    Integer countTotalWords();

    @Query(value = "SELECT w.id, w.name FROM word w WHERE w.id > :lastID LIMIT :limit", nativeQuery = true)
    List<Word> nextPage(@Param("lastID") int lastID, @Param("limit") int limit);

    @Query(value =
            "SELECT * FROM (SELECT w.id, w.name FROM word w WHERE w.id < :firstID ORDER BY w.id DESC LIMIT :limit) as word ORDER BY id asc",
            nativeQuery = true)
    List<Word> previousPage(@Param("firstID") int firstID, @Param("limit") int limit);

    // Phân trang bằng offset
    Page<Word> findAll(Pageable pageable);

    Optional<Word> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT w from Word w where w.name= ?1")
    Word findWordByName(String name);
}
