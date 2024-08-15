package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    List<Type> findByWordId(Integer wordID);
    @Query("select t from Type t where t.word.id= ?1")
    public List<Type> findByIdWord(int id);
    @Query(value = "SELECT DISTINCT t.type FROM dictionary.type t", nativeQuery = true)
    List<String> findAllTypeName();
}
