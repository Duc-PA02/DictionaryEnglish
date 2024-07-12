package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "synonyms")
public class Synonyms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_synonyms_id", foreignKey = @ForeignKey(name = "fk_antonyms_word_antonyms_id"))
    @JsonBackReference
    private Words synonym;

    @ManyToOne
    @JoinColumn(name = "word_id", foreignKey = @ForeignKey(name = "fk_antonyms_word_id"))
    @JsonBackReference
    private Words word;
}
