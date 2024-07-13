package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_searchhistory_user_id"))
    @JsonBackReference
    private Users user;

    @ManyToOne
    @JoinColumn(name = "word_id", foreignKey = @ForeignKey(name = "fk_searchhistory_word_id"))
    @JsonBackReference
    private Words word;

    @Column(nullable = false)
    private Integer total;
}
