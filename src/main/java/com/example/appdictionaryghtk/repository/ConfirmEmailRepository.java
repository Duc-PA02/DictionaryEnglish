package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.ConfirmEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmEmailRepository extends JpaRepository<ConfirmEmail, Integer> {
    ConfirmEmail findConfirmEmailByCode(String code);
}
