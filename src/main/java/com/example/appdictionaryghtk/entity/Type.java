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
@Table(name = "type")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String type;

    @ManyToOne
    @JoinColumn(name = "word_id", foreignKey = @ForeignKey(name = "fk_type_word"))
    @JsonBackReference
    private Words word;

    @OneToMany
    @JoinColumn(name = "definition_id", foreignKey = @ForeignKey(name = "fk_type_definition"))
    @JsonManagedReference
    private List<Definitions> definitionsList;

    @OneToMany
    @JoinColumn(name = "pronunciation_id", foreignKey = @ForeignKey(name = "fk_type_pronunciation"))
    @JsonManagedReference
    private List<Pronunciations> pronunciationsList;
}
