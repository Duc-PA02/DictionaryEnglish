package com.example.appdictionaryghtk.service.translate;


import com.example.appdictionaryghtk.entity.EnglishPrompt;

public interface ITranslateService {
    EnglishPrompt translate(EnglishPrompt englishPrompt, String sourceLanguage, String targetLanguage);
}
