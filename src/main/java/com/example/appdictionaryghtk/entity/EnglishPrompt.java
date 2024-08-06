package com.example.appdictionaryghtk.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnglishPrompt {
    @NotEmpty(message = "not empty")
    private String inputText;
    private String translatedText;
    private String inputVoice;
    private String translatedVoice;
}
