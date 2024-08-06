package com.example.appdictionaryghtk.controller.textGeneration;

import com.example.appdictionaryghtk.service.textGeneration.ITextGenerationService;
import com.example.appdictionaryghtk.entity.TextGenerationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/chatAI")
public class TextGenerationController {
    private final ITextGenerationService textGenerationService;

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> generateText(@RequestBody TextGenerationRequest textGenerationRequest) {
        return ResponseEntity.ok(textGenerationService.generateText(textGenerationRequest));
    }
}