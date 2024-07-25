package com.example.appdictionaryghtk.controller.translate;

import com.example.appdictionaryghtk.entity.EnglishPrompt;
import com.example.appdictionaryghtk.entity.Language;
import com.example.appdictionaryghtk.service.textToSpeech.ILanguageService;
import com.example.appdictionaryghtk.service.textToSpeech.ITextToSpeechService;
import com.example.appdictionaryghtk.service.translate.ITranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/translate")
public class TranslateController {

    private final ITranslateService translateService;

    private final ITextToSpeechService textToSpeechService;

    private final ILanguageService languageService;

    @GetMapping("/language")
    public List<Language> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    // Xử lý cả dịch và chuyển văn bản thành giọng nói
    @PostMapping("/{language}")
    public ResponseEntity<EnglishPrompt> processText(@RequestBody EnglishPrompt englishPrompt, @PathVariable String language) throws IOException {

        // Call the translate service
        EnglishPrompt translatedPrompt = translateService.translate(englishPrompt, language);

        // Call the text to speech service
        EnglishPrompt finalPromptInput = textToSpeechService.textToSpeech(translatedPrompt, language);

        // Call the text to speech service
        EnglishPrompt finalPromptOutput = textToSpeechService.textToSpeech(finalPromptInput, "English");

        // Return the final result
        return ResponseEntity.ok(finalPromptOutput);
    }
}
