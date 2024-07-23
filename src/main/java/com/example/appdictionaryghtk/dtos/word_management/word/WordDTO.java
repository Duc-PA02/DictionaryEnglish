package com.example.appdictionaryghtk.dtos.word_management.word;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class WordDTO {
    Integer id;
    String name;
}
