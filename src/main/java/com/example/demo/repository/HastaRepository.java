package com.example.demo.repository;

import com.example.demo.entity.Hasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HastaRepository extends JpaRepository<Hasta, Long> {
}