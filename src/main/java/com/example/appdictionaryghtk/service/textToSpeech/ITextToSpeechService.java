package com.example.appdictionaryghtk.service.textToSpeech;

import com.example.appdictionaryghtk.entity.EnglishPrompt;

import java.io.IOException;

public interface ITextToSpeechService {
    EnglishPrompt textToSpeech(EnglishPrompt englishPrompt, String targetLanguage, String label);
}
