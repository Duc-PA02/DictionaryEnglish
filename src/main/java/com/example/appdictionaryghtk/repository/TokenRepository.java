package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.entity.Token;
import com.example.appdictionaryghtk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findByUser(User user);
    Optional<Token> findByToken(String token);
    Token findByRefreshToken(String token);
}
