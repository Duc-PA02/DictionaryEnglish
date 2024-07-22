package com.example.appdictionaryghtk.dtos.elasticsearch;

import com.example.appdictionaryghtk.entity.Words;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "words")
public class WordsDTO {
    private Integer id;
    private String name;

    public static WordsDTO fromEntity(Words word) {
        return new WordsDTO(
                word.getId(),
                word.getName()
        );
    }
}
