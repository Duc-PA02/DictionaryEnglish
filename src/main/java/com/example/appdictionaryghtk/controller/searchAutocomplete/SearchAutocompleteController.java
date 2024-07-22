package com.example.appdictionaryghtk.controller.searchAutocomplete;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordsDTO;
import com.example.appdictionaryghtk.service.searchAutocomplete.ISearchAutocompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/dictionaryEnglish/search")
public class SearchAutocompleteController {

    private final ISearchAutocompleteService searchAutocompleteService;
    @GetMapping("/searchByKeyword")
    public List<WordsDTO> searchByKeywordAndSortByTotalDesc(@RequestParam String keyword){
        return searchAutocompleteService.searchByKeywordAndSortByTotalDesc(keyword);
    }

    @GetMapping("/searchByKeywordAndHistoryUser")
    public List<WordsDTO> searchWordIdsOrderByTotalDescByUserId(@RequestParam Integer userId,@RequestParam String keyword){
        return searchAutocompleteService.searchWordIdsOrderByTotalDescByUserId(userId, keyword);
    }
}
