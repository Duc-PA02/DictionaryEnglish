package com.example.appdictionaryghtk.service.word;

import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.repository.WordRepository;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl implements WordService{

    private WordRepository wordRepository;

    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public Word getWordByName(String name) {
        return wordRepository.findWordByName(name);
    }

    @Override
    public Word getWordById(int id) {
        return wordRepository.findById(id).get();
    }

}
