package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "definition")
public class Definitions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String definition;

    @Column(length = 255)
    private String examples;

    @ManyToOne
    @JoinColumn(name = "type_word_id", foreignKey = @ForeignKey(name = "fk_definition_type"))
    @JsonBackReference
    private Type type;
}
