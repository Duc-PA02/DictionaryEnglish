package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "word")
@Setter
@Getter
//@AllArgsConstructor
//@NoArgsConstructor
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 54, unique = true)
    private String name;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Synonyms> synonymsList;

    @OneToMany(mappedBy = "synonym", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Synonyms> synonymOf;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Antonyms> antonymsList;

    @OneToMany(mappedBy = "antonym", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Antonyms> antonymOf;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Type> typeList;

    @OneToMany(mappedBy = "words", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FavoriteWord> favoriteWordList;
}
