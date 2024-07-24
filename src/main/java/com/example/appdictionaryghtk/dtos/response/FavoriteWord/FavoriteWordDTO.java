package com.example.appdictionaryghtk.dtos.response.FavoriteWord;

import com.example.appdictionaryghtk.dtos.UserDTO;
import com.example.appdictionaryghtk.dtos.response.word.WordDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteWordDTO {
    private int id;
    private UserDTO userDTO;
    private WordDTO words;
}
