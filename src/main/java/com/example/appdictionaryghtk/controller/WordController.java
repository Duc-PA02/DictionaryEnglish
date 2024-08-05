package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.response.DefaultResponse;
import com.example.appdictionaryghtk.dtos.response.word.WordWithAntonymSynonymTypeResponse;
import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.service.word.IWordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/dictionary/english")
@RequiredArgsConstructor
public class WordController {
    private final IWordService iWordService;

    @GetMapping("/search/{name}")
    public ResponseEntity<DefaultResponse<WordWithAntonymSynonymTypeResponse>> getWordByName(@PathVariable String name){
        return ResponseEntity.ok(DefaultResponse.success("success",iWordService.getWordByName(name)));
    }

}
