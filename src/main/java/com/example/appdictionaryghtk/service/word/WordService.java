package com.example.appdictionaryghtk.service.word;

import com.example.appdictionaryghtk.entity.Word;
import com.example.appdictionaryghtk.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordService implements IWordService {

    private final WordRepository wordRepository;

    @Override
    public Word getWordByName(String name) {
        return wordRepository.findWordByName(name);
    }

    @Override
    public Word getWordById(int id) {
        return wordRepository.findById(id).get();
    }

}
