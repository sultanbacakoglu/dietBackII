package com.example.demo.repository;

import com.example.demo.entity.DiyetPlani;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiyetPlaniRepository extends JpaRepository<DiyetPlani, Long> {
    List<DiyetPlani> findByHastaId(Long hastaId);
    List<DiyetPlani> findByDiyetisyenId(Long diyetisyenId);
}
