package com.example.appdictionaryghtk.service.translate;


import com.example.appdictionaryghtk.entity.EnglishPrompt;

import java.io.IOException;

public interface ITranslateService {
    EnglishPrompt translate(EnglishPrompt englishPrompt, String sourceLanguage) throws IOException;
}
