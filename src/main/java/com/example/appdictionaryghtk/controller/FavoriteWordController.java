package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.response.FavoriteWord.FavoriteWordDTO;
import com.example.appdictionaryghtk.entity.FavoriteWord;
import com.example.appdictionaryghtk.service.favorite_word.IFavoriteWordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/favoriteword")
@RequiredArgsConstructor
public class FavoriteWordController {
    private final IFavoriteWordService favoriteWordService;
    private final ModelMapper modelMapper;
    @GetMapping("/{uid}")
    public ResponseEntity<?> getWordByName(@PathVariable int uid){
        List<FavoriteWord> favoriteWords = favoriteWordService.getFavoriteWord(uid);
        List<FavoriteWordDTO> favoriteWordDTOS = new ArrayList<>();
        for (FavoriteWord favoriteWord : favoriteWords){
            favoriteWordDTOS.add(modelMapper.map(favoriteWord, FavoriteWordDTO.class));
        }
        return ResponseEntity.ok(favoriteWordDTOS);
    }

    @DeleteMapping("/{fwid}")
    public void deleteFavoriteWord(@PathVariable int fwid){
        favoriteWordService.deleteFavoriteWord(fwid);
    }

    @PostMapping("/{uid}/{wid}")
    public ResponseEntity<String> addFavoriteWord(@PathVariable int uid, @PathVariable int wid) {
        String message = favoriteWordService.addFavoriteWord(uid, wid);
        return ResponseEntity.ok(message);
    }
}
