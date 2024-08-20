package com.example.appdictionaryghtk.controller.searchAutocomplete;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;
import com.example.appdictionaryghtk.service.elasticsearch.IElasticsearchWordsService;
import com.example.appdictionaryghtk.service.searchAutocomplete.ISearchAutocompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${api.prefix}/searchWord")
public class SearchAutocompleteController {

    private final ISearchAutocompleteService searchAutocompleteService;
    private final IElasticsearchWordsService elasticsearchWordsService;
    @GetMapping("/keyword")
    public ResponseEntity<List<WordDTO>> searchByKeywordAndSortByTotalDesc(@RequestParam("keyword") String keyword, @RequestParam("limit") Integer limit){
        return ResponseEntity.ok(searchAutocompleteService.searchByKeywordAndSortByTotalDesc(keyword, limit));
    }

    @GetMapping("/user")
    public ResponseEntity<List<WordDTO>> searchWordIdsOrderByTotalDescByUserId(@RequestParam("userId") Integer userId, @RequestParam("keyword") String keyword, @RequestParam("limit") Integer limit){
        return ResponseEntity.ok(searchAutocompleteService.searchWordIdsOrderByTotalDescByUserId(userId, keyword, limit));
    }

    @GetMapping("/fuzzy")
    public ResponseEntity<List<WordDTO>> searchByNameFuzzyOrPrefix(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(elasticsearchWordsService.searchByNameFuzzyOrPrefix(keyword));
    }
}
