package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@AllArgsConstructor
@NoArgsConstructor
public class Words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 54)
    private String name;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Synonyms> synonymsList;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Antonyms> antonymsList;
}
