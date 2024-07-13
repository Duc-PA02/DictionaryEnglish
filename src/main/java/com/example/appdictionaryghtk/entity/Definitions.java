package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "definition")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Definitions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String definition;

    private String examples;

    @ManyToOne
    @JoinColumn(name = "type_word_id", foreignKey = @ForeignKey(name = "fk_definition_type"))
    @JsonBackReference
    private Type type;
}
