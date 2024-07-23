package com.example.appdictionaryghtk.service.translate;

import com.example.appdictionaryghtk.entity.EnglishPrompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslateServiceImpl implements TranslateService {

    private final TranslateServiceApi translateServiceApi;


    public String getLanguageCode(String language) {
        switch (language.toLowerCase()) {
            case "afrikaans":
                return "af";
            case "albanian":
                return "sq";
            case "amharic":
                return "am";
            case "arabic":
                return "ar";
            case "armenian":
                return "hy";
            case "azerbaijani":
                return "az";
            case "basque":
                return "eu";
            case "belarusian":
                return "be";
            case "bengali":
                return "bn";
            case "bosnian":
                return "bs";
            case "bulgarian":
                return "bg";
            case "catalan":
                return "ca";
            case "cebuano":
                return "ceb";
            case "chichewa":
                return "ny";
            case "chinese (simplified)":
                return "zh-cn";
            case "chinese (traditional)":
                return "zh-tw";
            case "corsican":
                return "co";
            case "croatian":
                return "hr";
            case "czech":
                return "cs";
            case "danish":
                return "da";
            case "dutch":
                return "nl";
            case "english":
                return "en";
            case "esperanto":
                return "eo";
            case "estonian":
                return "et";
            case "filipino":
                return "tl";
            case "finnish":
                return "fi";
            case "french":
                return "fr";
            case "frisian":
                return "fy";
            case "galician":
                return "gl";
            case "georgian":
                return "ka";
            case "german":
                return "de";
            case "greek":
                return "el";
            case "gujarati":
                return "gu";
            case "haitian creole":
                return "ht";
            case "hausa":
                return "ha";
            case "hawaiian":
                return "haw";
            case "hebrew":
                return "he";
            case "hindi":
                return "hi";
            case "hmong":
                return "hmn";
            case "hungarian":
                return "hu";
            case "icelandic":
                return "is";
            case "igbo":
                return "ig";
            case "indonesian":
                return "id";
            case "irish":
                return "ga";
            case "italian":
                return "it";
            case "japanese":
                return "ja";
            case "javanese":
                return "jw";
            case "kannada":
                return "kn";
            case "kazakh":
                return "kk";
            case "khmer":
                return "km";
            case "korean":
                return "ko";
            case "kurdish (kurmanji)":
                return "ku";
            case "kyrgyz":
                return "ky";
            case "lao":
                return "lo";
            case "latin":
                return "la";
            case "latvian":
                return "lv";
            case "lithuanian":
                return "lt";
            case "luxembourgish":
                return "lb";
            case "macedonian":
                return "mk";
            case "malagasy":
                return "mg";
            case "malay":
                return "ms";
            case "malayalam":
                return "ml";
            case "maltese":
                return "mt";
            case "maori":
                return "mi";
            case "marathi":
                return "mr";
            case "mongolian":
                return "mn";
            case "myanmar (burmese)":
                return "my";
            case "nepali":
                return "ne";
            case "norwegian":
                return "no";
            case "odia":
                return "or";
            case "pashto":
                return "ps";
            case "persian":
                return "fa";
            case "polish":
                return "pl";
            case "portuguese":
                return "pt";
            case "punjabi":
                return "pa";
            case "romanian":
                return "ro";
            case "russian":
                return "ru";
            case "samoan":
                return "sm";
            case "scots gaelic":
                return "gd";
            case "serbian":
                return "sr";
            case "sesotho":
                return "st";
            case "shona":
                return "sn";
            case "sindhi":
                return "sd";
            case "sinhala":
                return "si";
            case "slovak":
                return "sk";
            case "slovenian":
                return "sl";
            case "somali":
                return "so";
            case "spanish":
                return "es";
            case "sundanese":
                return "su";
            case "swahili":
                return "sw";
            case "swedish":
                return "sv";
            case "tajik":
                return "tg";
            case "tamil":
                return "ta";
            case "telugu":
                return "te";
            case "thai":
                return "th";
            case "turkish":
                return "tr";
            case "ukrainian":
                return "uk";
            case "urdu":
                return "ur";
            case "uyghur":
                return "ug";
            case "uzbek":
                return "uz";
            case "vietnamese":
                return "vi";
            case "welsh":
                return "cy";
            case "xhosa":
                return "xh";
            case "yiddish":
                return "yi";
            case "yoruba":
                return "yo";
            case "zulu":
                return "zu";
            default:
                throw new IllegalArgumentException("Invalid language: " + language);
        }
    }

    @Override
    public EnglishPrompt translate(EnglishPrompt englishPrompt, String language) throws IOException {
        String sourceLanguage = getLanguageCode(language);

        String response = translateServiceApi.translateText(
                "gtx",
                sourceLanguage,
                "en", // Ngôn ngữ đích luôn là "en"
                "t",
                englishPrompt.getInputText()
        ).toString();
        log.info("Response body: {}", response);

        try {
            // Phân tích cú pháp phản hồi JSON từ Google Translate
            JSONArray jsonArray = new JSONArray(response);
            if (jsonArray.length() > 0) {
                String translatedText = jsonArray.getString(0);
                englishPrompt.setTranslatedText(translatedText);
            } else {
                log.info("No translation found in jsonArray");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return englishPrompt;
    }
}