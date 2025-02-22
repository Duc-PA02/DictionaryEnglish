package com.example.appdictionaryghtk.dtos.elasticsearch;

import com.example.appdictionaryghtk.entity.Word;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "word")
public class WordDTO {
    @Id
    private Integer id;
    private String name;

    public static WordDTO fromEntity(Word word) {
        return new WordDTO(
                word.getId(),
                word.getName()
        );
    }
}
