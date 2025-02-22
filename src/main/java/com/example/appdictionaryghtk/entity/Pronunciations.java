package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pronunciation")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pronunciations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 2)
    private String region;

    @Column(length = 100)
    private String audio;

    @Column(nullable = false, length = 50)
    private String pronunciation;

    @ManyToOne
    @JoinColumn(name = "type_word_id", foreignKey = @ForeignKey(name = "fk_pronunciation_type_word_id"))
    @JsonBackReference
    private Type type;
}
