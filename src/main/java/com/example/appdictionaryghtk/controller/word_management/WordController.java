package com.example.appdictionaryghtk.controller.word_management;

import com.example.appdictionaryghtk.dtos.word_management.word.WordDetail;
import com.example.appdictionaryghtk.service.word_management.WordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("adminwordcontroller")
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WordController {
    WordService wordService;

    @GetMapping("/words/{pageNumber}/{pageSize}")
    public Page<WordDetail> findAll(@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
        return wordService.findAll(pageNumber, pageSize, null);
    }

    @GetMapping("/words/{id}")
    public ResponseEntity<WordDetail> getWord(@PathVariable("id") Integer id){
        return ResponseEntity.ok(wordService.findByID(id));
    }

    @PostMapping("/words")
    public ResponseEntity<WordDetail> create(@RequestBody WordDetail wordDetail){
        return ResponseEntity.ok(wordService.create(wordDetail));
    }

    @PutMapping("/words/{wordID}")
    public ResponseEntity<WordDetail> update(@PathVariable("wordID") Integer wordID, @RequestBody WordDetail wordDetail){
        return ResponseEntity.ok(wordService.update(wordID, wordDetail));
    }

    @DeleteMapping("/words/{id}")
    public ResponseEntity<?> deleteWord(@PathVariable("id") Integer id){
        wordService.deleteByID(id);
        return ResponseEntity.ok("Delete Successful");
    }

}
