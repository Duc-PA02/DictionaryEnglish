package com.example.appdictionaryghtk.service.textToSpeech;

import com.example.appdictionaryghtk.entity.EnglishPrompt;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Service
@Slf4j
public class TextToSpeechService implements ITextToSpeechService {

    // Tiêm giá trị từ file cấu hình vào biến apiKey
    @Value("${textToSpeechApi.key}")
    private String apiKey;

    @Autowired
    private ITextToSpeechServiceApi textToSpeechServiceApi;

    @Value("${firebase.bucket.name}")
    private String bucketName;

    private Storage storage;

    public String getLanguageCode(String language) {
        switch (language.toLowerCase()) {
            case "arabic":
                return "ar-sa";
            case "bulgarian":
                return "bg-bg";
            case "catalan":
                return "ca-es";
            case "chinese simplified":
                return "zh-cn";
            case "chinese traditional":
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

    public TextToSpeechService() throws IOException {
        this.textToSpeechServiceApi = textToSpeechServiceApi;
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ClassPathResource(
                        "translate-ghtk-firebase-adminsdk-apy68-c6f9907ae6.json")
                        .getInputStream());
        storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    @Override
    public EnglishPrompt textToSpeech(EnglishPrompt englishPrompt, String language, String label){
        String text;

        if (label.equals("input")) {
            text = englishPrompt.getInputText();
        } else {
            text = englishPrompt.getTranslatedText();
        }

        byte[] audioData;
        try {
            String targetLanguage = getLanguageCode(language);
            audioData = textToSpeechServiceApi.textToSpeech(
                    apiKey,
                    "96f7627a9a16464fb5318909083ae7f0",
                    text,
                    targetLanguage,
                    "-3",
                    "mp3",
                    "8khz_8bit_mono");

            createAudioFromByteArray(audioData, englishPrompt, targetLanguage, label);
        } catch (Exception e) {
            log.error("Error during text-to-speech conversion: {}", e.getMessage());
            // return null; // Trả về null nếu gặp lỗi
        }
        return englishPrompt;
    }

    private void createAudioFromByteArray(byte[] audioData, EnglishPrompt englishPrompt, String targetLanguage, String label) {
        String randomFileName = randomFile() + "_" + targetLanguage + ".mp3";

        // Tải lên Firebase Storage
        try {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, "audio/" + randomFileName).build();
            storage.create(blobInfo, audioData);

            // Tạo URL truy cập trực tiếp tệp
            String firebaseUrl = "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/"
                    + java.net.URLEncoder.encode("audio/" + randomFileName, "UTF-8")
                    + "?alt=media";

            if ("input".equals(label)) {
                englishPrompt.setInputVoice(firebaseUrl);
            } else {
                englishPrompt.setTranslatedVoice(firebaseUrl);
            }
            log.info("Firebase URL: {}", firebaseUrl);
        } catch (Exception e) {
            log.error("Error uploading file to Firebase Storage: {}", e.getMessage());
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