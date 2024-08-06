package com.example.appdictionaryghtk.repository;

import com.example.appdictionaryghtk.dtos.response.role.RoleResponse;
import com.example.appdictionaryghtk.entity.Role;
import com.example.appdictionaryghtk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole (String role);
    List<Role> findByUsers(User user);
}
