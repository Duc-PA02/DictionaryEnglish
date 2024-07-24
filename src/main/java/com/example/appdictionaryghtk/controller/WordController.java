package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.response.word.WordWithAntonymSynonymTypeDTO;
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
    private final ModelMapper modelMapper;
    private final IWordService IWordService;

    @GetMapping("/search/{name}")
    public ResponseEntity<?> getWordByName(@PathVariable String name){
        Word word = IWordService.getWordByName(name);

        WordWithAntonymSynonymTypeDTO wordDTO = modelMapper.map(word, WordWithAntonymSynonymTypeDTO.class);
        return ResponseEntity.ok(wordDTO);
    }

}
