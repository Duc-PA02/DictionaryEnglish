package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "favorite_word")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_favoriteword_user_id"))
    @JsonBackReference
    private Users user;

    @ManyToOne
    @JoinColumn(name = "word_id", foreignKey = @ForeignKey(name = "fk_favoriteword_word_id"))
    @JsonBackReference
    private Words words;
}
