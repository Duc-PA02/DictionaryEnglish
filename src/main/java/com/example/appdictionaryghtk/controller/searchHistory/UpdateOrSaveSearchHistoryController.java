package com.example.appdictionaryghtk.controller.searchHistory;

import com.example.appdictionaryghtk.entity.SearchHistory;
import com.example.appdictionaryghtk.service.searchHistory.ISearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${api.prefix}/searchWord")
public class UpdateOrSaveSearchHistoryController {
    private final ISearchHistoryService searchHistoryService;

    @PostMapping("/save")
    public SearchHistory UpdateOrSaveSearchHistory(@RequestParam("userId") Integer userId, @RequestParam("wordId") Integer wordId){
        return searchHistoryService.updateOrSaveSearchHistory(userId, wordId);
    }
}
