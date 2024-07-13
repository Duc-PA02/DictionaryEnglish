package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "search_statistic")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "word_id", foreignKey = @ForeignKey(name = "fk_searchstatistic_word_id"))
    @JsonBackReference
    private Words word;

    @Column(nullable = false)
    private Integer total;
}
