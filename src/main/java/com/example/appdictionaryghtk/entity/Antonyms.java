package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "antonyms")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Antonyms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_antonyms_id", foreignKey = @ForeignKey(name = "fk_antonyms_antonyms_id"))
    @JsonBackReference
    private Word antonym;

    @ManyToOne
    @JoinColumn(name = "word_id", foreignKey = @ForeignKey(name = "fk_antonyms_word_id"))
    @JsonBackReference
    private Word word;
}

