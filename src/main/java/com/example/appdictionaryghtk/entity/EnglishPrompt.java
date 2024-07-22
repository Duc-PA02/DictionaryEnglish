package com.example.appdictionaryghtk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnglishPrompt {
    private String inputText;
    private String translatedText;
    private String inputVoice;
    private String translatedVoice;
}
