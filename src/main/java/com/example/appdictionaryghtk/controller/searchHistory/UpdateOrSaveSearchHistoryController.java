package com.example.appdictionaryghtk.controller.searchHistory;

import com.example.appdictionaryghtk.entity.SearchHistory;
import com.example.appdictionaryghtk.service.searchHistory.ISearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${api.prefix}/searchWord")
public class UpdateOrSaveSearchHistoryController {
    private final ISearchHistoryService searchHistoryService;

    @PostMapping("/save")
    public ResponseEntity<SearchHistory> UpdateOrSaveSearchHistory(@RequestParam("userId") Integer userId, @RequestParam("wordId") Integer wordId){
        return ResponseEntity.ok(searchHistoryService.updateOrSaveSearchHistory(userId, wordId));
    }
}
