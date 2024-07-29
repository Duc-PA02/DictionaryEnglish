package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Definitions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefinitionRepository extends JpaRepository<Definitions, Integer> {
    List<Definitions> findByTypeId(Integer typeid);
}
