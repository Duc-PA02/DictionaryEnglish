package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElasticsearchWordsRepositoty extends ElasticsearchRepository<WordDTO, Integer> {
    @Query("{\"prefix\": {\"name\": \"?0\"}}")
    List<WordDTO> searchByKeyword(@Param("keyword") String keyword);

    @Query("{\"bool\": {\"should\": [{\"fuzzy\": {\"name\": {\"value\": \"?0\", \"fuzziness\": \"2\"}}}, {\"prefix\": {\"name\": \"?1\"}}], \"minimum_should_match\": 1}}")
    List<WordDTO> findByNameFuzzyOrPrefix(String value, String prefix);
}