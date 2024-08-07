package com.example.appdictionaryghtk.controller;

import com.example.appdictionaryghtk.dtos.response.DefaultResponse;
import com.example.appdictionaryghtk.dtos.response.favoriteword.FavoriteWordResponse;
import com.example.appdictionaryghtk.service.favorite_word.IFavoriteWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/favoriteword")
@RequiredArgsConstructor
public class FavoriteWordController {
    private final IFavoriteWordService favoriteWordService;

    @DeleteMapping("/{fwid}")
    public void deleteFavoriteWord(@PathVariable int fwid){
        favoriteWordService.deleteFavoriteWord(fwid);
    }

    @DeleteMapping("/delete_all/{uid}")
    public void deleteFavoriteWordByUser(@PathVariable int uid){
        favoriteWordService.deleteAllFavoriteByUser(uid);
    }

    @PostMapping("/{uid}/{wid}")
    public ResponseEntity<DefaultResponse<FavoriteWordResponse>> addFavoriteWord(@PathVariable int uid, @PathVariable int wid) {
        return ResponseEntity.ok(DefaultResponse.success("Add to favorite success",favoriteWordService.addFavoriteWord(uid,wid)));
    }

    @GetMapping("/{uid}")
    public ResponseEntity<DefaultResponse<List<FavoriteWordResponse>>> getFavoriteByUserIdAndWordsNameContaining(@PathVariable int uid, @RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false, defaultValue = "id") String sortDirection){
        return ResponseEntity.ok(DefaultResponse.success("Success", favoriteWordService.getFavoriteByUserIdAndWordsNameContaining(uid, name, sortDirection)));
    }
}
