package com.example.appdictionaryghtk.service.word;

import com.example.appdictionaryghtk.entity.Word;


public interface WordService {

    public Word getWordByName(String name);

    public Word getWordById(int id);
}
