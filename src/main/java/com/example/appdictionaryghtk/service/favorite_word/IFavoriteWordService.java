package com.example.appdictionaryghtk.service.favorite_word;

import com.example.appdictionaryghtk.dtos.response.favoriteword.FavoriteWordResponse;

import java.util.List;

public interface IFavoriteWordService {

    public List<FavoriteWordResponse> getFavoriteWordByUid(int uid);

    public List<FavoriteWordResponse> getFavoriteWordByUidSortByWordNameDESC(int uid);

    public List<FavoriteWordResponse> getFavoriteWordByUidSortByWordNameASC(int uid);

    public void deleteFavoriteWord(int fwid);

    public void deleteAllFavoriteByUser(int uid);
    public FavoriteWordResponse addFavoriteWord(int uid, int wid);

}
