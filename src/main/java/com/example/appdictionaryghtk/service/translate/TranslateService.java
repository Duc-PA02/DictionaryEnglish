package com.example.appdictionaryghtk.service.translate;


import com.example.appdictionaryghtk.entity.EnglishPrompt;

import java.io.IOException;

public interface TranslateService {
    EnglishPrompt translate(EnglishPrompt englishPrompt, String sourceLanguage) throws IOException;
}
