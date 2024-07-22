package com.example.appdictionaryghtk.controller.translate;

import com.example.appdictionaryghtk.entity.EnglishPrompt;
import com.example.appdictionaryghtk.service.textToSpeech.TextToSpeechService;
import com.example.appdictionaryghtk.service.translate.TranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionaryEnglish/translate")
public class TranslateController {

    private final TranslateService translateService;

    private final TextToSpeechService textToSpeechService;

    // Process both translate and text to speech
    @PostMapping("/{language}")
    public EnglishPrompt processText(@RequestBody EnglishPrompt englishPrompt, @PathVariable String language) throws IOException {

        // Call the translate service
        EnglishPrompt translatedPrompt = translateService.translate(englishPrompt, language);

        // Call the text to speech service
        EnglishPrompt finalPromptInput = textToSpeechService.textToSpeech(translatedPrompt, language);

        // Call the text to speech service
        EnglishPrompt finalPromptOutput = textToSpeechService.textToSpeech(finalPromptInput, "English");

        // Return the final result
        return finalPromptOutput;
    }
}
