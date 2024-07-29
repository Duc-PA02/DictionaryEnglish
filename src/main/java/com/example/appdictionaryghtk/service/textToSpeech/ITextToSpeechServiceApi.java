package com.example.appdictionaryghtk.service.textToSpeech;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "textToSpeechClient", url = "https://voicerss-text-to-speech.p.rapidapi.com")
public interface ITextToSpeechServiceApi {
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    byte[] textToSpeech(
            @RequestHeader("X-RapidAPI-Key") String apiKey,
            @RequestParam("key") String key,
            @RequestParam("src") String src,
            @RequestParam("hl") String hl,
            @RequestParam("r") String r,
            @RequestParam("c") String c,
            @RequestParam("f") String f
    );
}
