package com.example.appdictionaryghtk.service.word;

import com.example.appdictionaryghtk.dtos.response.word.WordWithAntonymSynonymTypeResponse;
import com.example.appdictionaryghtk.entity.Word;


public interface IWordService {

    public WordWithAntonymSynonymTypeResponse getWordByName(String name);

    public WordWithAntonymSynonymTypeResponse getWordById(int id);
}
