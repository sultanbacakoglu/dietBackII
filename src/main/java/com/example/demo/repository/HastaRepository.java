package com.example.demo.repository;

import com.example.demo.entity.Hasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HastaRepository extends JpaRepository<Hasta, Long> {
    List<Hasta> findByDiyetisyenId(Long diyetisyenId);
}