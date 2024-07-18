package com.example.appdictionaryghtk.dtos.word_management.pronunciation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PronunciationDTO {
    Integer id;

    String region;

    String audio;

    String pronunciation;
}
