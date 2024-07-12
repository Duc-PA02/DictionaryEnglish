package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "antonyms")
public class Antonyms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_antonyms_id", foreignKey = @ForeignKey(name = "fk_antonyms_antonyms_id"))
    @JsonBackReference
    private Words antonym;

    @ManyToOne
    @JoinColumn(name = "word_id", foreignKey = @ForeignKey(name = "fk_antonyms_word_id"))
    @JsonBackReference
    private Words word;
}
