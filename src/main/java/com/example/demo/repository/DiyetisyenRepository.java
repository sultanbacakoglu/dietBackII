package com.example.demo.repository;

import com.example.demo.entity.Diyetisyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiyetisyenRepository extends JpaRepository<Diyetisyen, Long> {
    Optional<Diyetisyen> findByEposta(String eposta);
}