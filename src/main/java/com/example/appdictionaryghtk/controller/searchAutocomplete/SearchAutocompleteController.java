package com.example.appdictionaryghtk.controller.searchAutocomplete;

import com.example.appdictionaryghtk.dtos.elasticsearch.WordsDTO;
import com.example.appdictionaryghtk.service.searchAutocomplete.ISearchAutocompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${api.prefix}/searchWord")
public class SearchAutocompleteController {

    private final ISearchAutocompleteService searchAutocompleteService;
    @GetMapping("/keyword")
    public List<WordsDTO> searchByKeywordAndSortByTotalDesc(@RequestParam("keyword") String keyword, @RequestParam("limit") Integer limit){
        return searchAutocompleteService.searchByKeywordAndSortByTotalDesc(keyword, limit);
    }

    @GetMapping("/user")
    public List<WordsDTO> searchWordIdsOrderByTotalDescByUserId(@RequestParam("userId") Integer userId, @RequestParam("keyword") String keyword, @RequestParam("limit") Integer limit){
        return searchAutocompleteService.searchWordIdsOrderByTotalDescByUserId(userId, keyword, limit);
    }
}
