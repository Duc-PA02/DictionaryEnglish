package com.example.appdictionaryghtk.controller.word_management;

import com.example.appdictionaryghtk.dtos.word_management.type.TypeDTO;
import com.example.appdictionaryghtk.dtos.word_management.word.WordDTO;
import com.example.appdictionaryghtk.dtos.word_management.word.WordDetail;
import com.example.appdictionaryghtk.service.word_management.WordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("wordManagementController")
@RequestMapping("${api.prefix}/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WordController {
    WordService wordService;

    @GetMapping("/words/after")
    public List<WordDTO> getWordsAfter(@RequestParam(required = false, defaultValue ="0") Integer lastId,
                                    @RequestParam(defaultValue = "10") int limit) {
        return wordService.getWordsAfter(lastId, limit);
    }

    @GetMapping("/words/before")
    public List<WordDTO> getWordsBefore(@RequestParam(required = false) Integer firstId,
                                        @RequestParam(defaultValue = "10") int limit) {
        return wordService.getWordsBefore(firstId, limit);
    }

    @GetMapping("/words/page")
    public Page<WordDTO> getWordsByPage(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int limit) {
        return wordService.getWordsByPage(page, limit);
    }


    @GetMapping("/words/{id}")
    public ResponseEntity<WordDetail> getWord(@PathVariable("id") Integer id){
        return ResponseEntity.ok(wordService.findByID(id));
    }

    @GetMapping("/words")
    public ResponseEntity<List<WordDTO>> getWord(@RequestParam("name") String name){
        return ResponseEntity.ok(wordService.findByName(name));
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
