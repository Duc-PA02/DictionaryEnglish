package com.example.appdictionaryghtk.controller.searchAutocomplete;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordDTO;
import com.example.appdictionaryghtk.service.searchAutocomplete.ISearchAutocompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${api.prefix}/searchWord")
public class SearchAutocompleteController {

    private final ISearchAutocompleteService searchAutocompleteService;
    @GetMapping("/keyword")
    public ResponseEntity<List<WordDTO>> searchByKeywordAndSortByTotalDesc(@RequestParam("keyword") String keyword, @RequestParam("limit") Integer limit) throws IOException {
        return ResponseEntity.ok(searchAutocompleteService.searchByKeywordAndSortByTotalDesc(keyword, limit));
    }

    @GetMapping("/user")
    public ResponseEntity<List<WordDTO>> searchWordIdsOrderByTotalDescByUserId(@RequestParam("userId") Integer userId, @RequestParam("keyword") String keyword, @RequestParam("limit") Integer limit) throws IOException {
        return ResponseEntity.ok(searchAutocompleteService.searchWordIdsOrderByTotalDescByUserId(userId, keyword, limit));
    }
}
