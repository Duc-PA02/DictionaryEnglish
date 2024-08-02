package com.example.appdictionaryghtk.service.textGeneration;

import com.example.appdictionaryghtk.entity.TextGenerationRequest;
import com.example.appdictionaryghtk.entity.TextGenerationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "textGenerationServiceApi", url = "https://generativelanguage.googleapis.com/v1beta")
public interface TextGenerationServiceApi {
    @PostMapping("/models/gemini-1.5-flash:generateContent?key={apiKey}")
    TextGenerationResponse generateContent(@RequestBody TextGenerationRequest request, @PathVariable("apiKey") String apiKey);
}