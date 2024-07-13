package com.example.appdictionaryghtk.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 15)
    private String username;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 30)
    private String fullname;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    private String avatar;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(length = 10)
    private String status;

    @ManyToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user_role"))
    @JsonBackReference
    private Role role;
}
