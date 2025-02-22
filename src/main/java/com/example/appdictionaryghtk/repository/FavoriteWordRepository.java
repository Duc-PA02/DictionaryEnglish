package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.FavoriteWord;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.entity.Word;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteWordRepository extends JpaRepository<FavoriteWord, Integer> {

    List<FavoriteWord> findByUserId(int userId, Sort sort);
    boolean existsByWordsAndUser(Word word, User user);
    boolean existsByWordsNameContainingAndUserId(String name, int uid);
    void deleteByUser_Id(int uid);

    List<FavoriteWord> findByUserIdAndWordsNameContaining(int uid,String name);
}
