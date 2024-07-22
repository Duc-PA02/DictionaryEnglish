package com.example.appdictionaryghtk.controller.searchHistory;

import com.example.appdictionaryghtk.entity.SearchHistory;
import com.example.appdictionaryghtk.service.searchHistory.ISearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/dictionaryEnglish/search")
public class UpdateOrSaveSearchHistoryController {
    private final ISearchHistoryService searchHistoryService;

    @PostMapping("/UpdateOrSaveSearchHistory")
    public SearchHistory UpdateOrSaveSearchHistory(@RequestParam Integer userId, @RequestParam Integer wordId){
        return searchHistoryService.updateOrSaveSearchHistory(userId, wordId);
    }
}
