package com.example.appdictionaryghtk.controller.word_management;

import com.example.appdictionaryghtk.dtos.response.ResponseObject;
import com.example.appdictionaryghtk.dtos.word_management.type.TypeDTO;
import com.example.appdictionaryghtk.dtos.word_management.word.WordDTO;
import com.example.appdictionaryghtk.dtos.word_management.word.WordDetail;
import com.example.appdictionaryghtk.service.word_management.WordService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("wordManagementController")
@RequestMapping("${api.prefix}/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WordController {
    WordService wordService;

    @GetMapping("/words/after")
    public ResponseObject getWordsAfter(@RequestParam(required = false, defaultValue ="0") Integer lastId,
                                              @RequestParam(defaultValue = "10") int limit) {
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(wordService.getWordsAfter(lastId, limit))
                .build();
    }

    @GetMapping("/words/before")
    public ResponseObject getWordsBefore(@RequestParam(required = false) Integer firstId,
                                        @RequestParam(defaultValue = "10") int limit) {
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(wordService.getWordsBefore(firstId, limit))
                .build();
    }

    @GetMapping("/words/page")
    public ResponseObject getWordsByPage(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int limit) {
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(wordService.getWordsByPage(page, limit))
                .build();
    }


    @GetMapping("/words/{id}")
    public ResponseObject getWord(@PathVariable("id") Integer id){
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(wordService.findByID(id))
                .build();
    }

    @GetMapping("/words")
    public ResponseObject getWord(@RequestParam("name") String name){
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(wordService.findByName(name))
                .build();
    }


    @PostMapping("/words")
    public ResponseObject create(@RequestBody WordDetail wordDetail){
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(wordService.create(wordDetail))
                .build();
    }

    @PutMapping("/words/{wordID}")
    public ResponseObject update(@PathVariable("wordID") Integer wordID, @RequestBody WordDetail wordDetail){
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(wordService.update(wordID, wordDetail))
                .build();
    }

    @DeleteMapping("/words/{id}")
    public ResponseObject deleteWord(@PathVariable("id") Integer id){
        wordService.deleteByID(id);
        return ResponseObject.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data("Delete Successful")
                .build();
    }

}
