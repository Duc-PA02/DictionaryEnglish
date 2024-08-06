package com.example.appdictionaryghtk.service.textToSpeech;

import com.example.appdictionaryghtk.entity.Language;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService implements ILanguageService {

    @Override
    public List<Language> getAllLanguages() {
        return Arrays.asList(Language.values());
    }
}
