package com.example.appdictionaryghtk.service.favorite_word;

import com.example.appdictionaryghtk.entity.FavoriteWord;

import java.util.List;

public interface IFavoriteWordService {

    public List<FavoriteWord> getFavoriteWord(int uid);

    public void deleteFavoriteWord(int fwid);

    public String addFavoriteWord(int uid, int wid);

}
