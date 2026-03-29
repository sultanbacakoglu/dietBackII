package com.example.demo.repository;

import com.example.demo.entity.Diyetisyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiyetisyenRepository extends JpaRepository<Diyetisyen, Long> {
}