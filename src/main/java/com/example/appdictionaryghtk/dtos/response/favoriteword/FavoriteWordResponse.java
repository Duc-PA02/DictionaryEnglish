package com.example.appdictionaryghtk.dtos.response.favoriteword;

import com.example.appdictionaryghtk.dtos.UserDTO;
import com.example.appdictionaryghtk.dtos.response.word.WordResponse;
import com.example.appdictionaryghtk.dtos.response.word.WordWithAntonymSynonymTypeResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteWordResponse {
    private int id;
    private UserDTO user;
    private WordWithAntonymSynonymTypeResponse words;
}
