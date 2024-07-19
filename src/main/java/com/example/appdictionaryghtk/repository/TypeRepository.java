package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    List<Type> findByWordId(Integer wordID);
}
