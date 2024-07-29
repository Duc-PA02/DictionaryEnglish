package com.example.appdictionaryghtk.service.textToSpeech;

import com.example.appdictionaryghtk.entity.EnglishPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextToSpeechService implements ITextToSpeechService {

    // Tiêm giá trị từ file cấu hình vào biến apiKey
    @Value("${textToSpeechApi.key}")
    private String apiKey;

    private final ITextToSpeechServiceApi textToSpeechServiceApi;

    public String getLanguageCode(String language) {
        switch (language.toLowerCase()) {
            case "arabic":
                return "ar-sa";
            case "bulgarian":
                return "bg-bg";
            case "catalan":
                return "ca-es";
            case "chinese (simplified)":
                return "zh-cn";
            case "chinese (traditional)":
                return "zh-tw";
            case "croatian":
                return "hr-hr";
            case "czech":
                return "cs-cz";
            case "danish":
                return "da-dk";
            case "dutch":
                return "nl-nl";
            case "english":
                return "en-us";
            case "finnish":
                return "fi-fi";
            case "french":
                return "fr-fr";
            case "german":
                return "de-de";
            case "greek":
                return "el-gr";
            case "hebrew":
                return "he-il";
            case "hindi":
                return "hi-in";
            case "hungarian":
                return "hu-hu";
            case "indonesian":
                return "id-id";
            case "italian":
                return "it-it";
            case "japanese":
                return "ja-jp";
            case "korean":
                return "ko-kr";
            case "malay":
                return "ms-my";
            case "norwegian":
                return "nb-no";
            case "polish":
                return "pl-pl";
            case "portuguese":
                return "pt-pt";
            case "romanian":
                return "ro-ro";
            case "russian":
                return "ru-ru";
            case "slovak":
                return "sk-sk";
            case "slovenian":
                return "sl-si";
            case "spanish":
                return "es-es";
            case "swedish":
                return "sv-se";
            case "tamil":
                return "ta-in";
            case "thai":
                return "th-th";
            case "turkish":
                return "tr-tr";
            case "vietnamese":
                return "vi-vn";
            default:
                throw new IllegalArgumentException("Invalid language: " + language);
        }
    }

    @Override
    public EnglishPrompt textToSpeech(EnglishPrompt englishPrompt, String language) throws IOException {
        String text;
        String targetLanguage = getLanguageCode(language);

        if (targetLanguage.equals("en-us")) {
            text = englishPrompt.getTranslatedText();
        } else {
            text = englishPrompt.getInputText();
        }

        byte[] audioData = textToSpeechServiceApi.textToSpeech(
                apiKey,
                "96f7627a9a16464fb5318909083ae7f0",
                text,
                targetLanguage,
                "0",
                "mp3",
                "8khz_8bit_mono"
        );

        createAudioFromByteArray(audioData, englishPrompt, targetLanguage);
        return englishPrompt;
    }

    private void createAudioFromByteArray(byte[] audioData, EnglishPrompt englishPrompt, String targetLanguage) throws IOException {
        Path resourceDirectory = Paths.get("src", "main", "resources", "static", "audio");
        if (!Files.exists(resourceDirectory)) {
            Files.createDirectories(resourceDirectory);
        }
        Path filePath = resourceDirectory.resolve(randomFile() + "_" + targetLanguage + ".mp3");

        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
            FileCopyUtils.copy(audioData, outputStream);

            if (targetLanguage.equals("en-us")) {
                englishPrompt.setTranslatedVoice(filePath.toString());
            } else {
                englishPrompt.setInputVoice(filePath.toString());
            }
            log.info("Path: {}" , filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating audio file: " + e.getMessage());
        }
    }

    public String randomFile() {
        int length = 30;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }
}