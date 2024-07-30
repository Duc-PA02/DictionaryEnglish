package com.example.appdictionaryghtk.dtos.elasticsearch;

import com.example.appdictionaryghtk.entity.Word;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "word")
public class WordDTO {
    private Integer id;
    private String name;

    public static WordDTO fromEntity(Word word) {
        return new WordDTO(
                word.getId(),
                word.getName()
        );
    }
}
