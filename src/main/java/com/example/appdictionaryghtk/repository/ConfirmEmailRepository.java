package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.ConfirmEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmail, Integer> {
    ConfirmEmail findByCode(String code);
    Optional<ConfirmEmail> findByUser_EmailAndExpiredTimeAfter(String email, LocalDateTime now);
}
