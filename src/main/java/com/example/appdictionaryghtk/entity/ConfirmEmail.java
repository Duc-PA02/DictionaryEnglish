package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "confirm_email")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    @Column(name = "is_confirm")
    private Boolean isConfirm;
    @Column(name = "requiredtime")
    private LocalDateTime requiredTime;
    @Column(name = "expiredtime")
    private LocalDateTime expiredTime;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_confirmEmail_user"))
    @JsonBackReference
    private Users user;
}
