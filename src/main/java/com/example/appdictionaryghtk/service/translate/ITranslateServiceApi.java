package com.example.appdictionaryghtk.service.translate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "translateServiceApi", url = "https://clients5.google.com")
public interface ITranslateServiceApi {

    @GetMapping("/translate_a/t")
    String translateText(
            @RequestParam("client") String client,
            @RequestParam("sl") String sourceLanguage,
            @RequestParam("tl") String targetLanguage,
            @RequestParam("dt") String dt,
            @RequestParam("q") String text
    );
}
