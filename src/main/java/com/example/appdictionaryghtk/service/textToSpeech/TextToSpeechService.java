package com.example.appdictionaryghtk.service.textToSpeech;

import com.example.appdictionaryghtk.entity.EnglishPrompt;

import java.io.IOException;

public interface TextToSpeechService {
    EnglishPrompt textToSpeech(EnglishPrompt englishPrompt, String targetLanguage) throws IOException;
}
