package com.example.appdictionaryghtk.service.favorite_word;

import com.example.appdictionaryghtk.dtos.response.favoriteword.FavoriteWordResponse;

import java.util.List;

public interface IFavoriteWordService {
    public void deleteFavoriteWord(int fwid);

    public void deleteAllFavoriteByUser(int uid);
    public FavoriteWordResponse addFavoriteWord(int uid, int wid);

    public List<FavoriteWordResponse> getFavoriteByUserIdAndWordsNameContaining(int uid, String name, String sortDirection);
}
