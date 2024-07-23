package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.word_management.word.WordDetail;
import com.example.appdictionaryghtk.service.word.WordServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WordController {
    WordServiceImpl wordServiceImpl;

    @GetMapping("/words/{pageNumber}/{pageSize}")
    public Page<WordDetail> findAll(@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
        return wordServiceImpl.findAll(pageNumber, pageSize, null);
    }

    @GetMapping("/words/{id}")
    public ResponseEntity<WordDetail> getWord(@PathVariable("id") Integer id){
        return ResponseEntity.ok(wordServiceImpl.findByID(id));
    }

    @PostMapping("/words")
    public ResponseEntity<WordDetail> create(@RequestBody WordDetail wordDetail){
        return ResponseEntity.ok(wordServiceImpl.create(wordDetail));
    }

    @PutMapping("/words/{wordID}")
    public ResponseEntity<WordDetail> update(@PathVariable("wordID") Integer wordID, @RequestBody WordDetail wordDetail){
        return ResponseEntity.ok(wordServiceImpl.update(wordID, wordDetail));
    }

    @DeleteMapping("/words/{id}")
    public ResponseEntity<?> deleteWord(@PathVariable("id") Integer id){
        wordServiceImpl.deleteByID(id);
        return ResponseEntity.ok("Delete Successful");
    }

}
