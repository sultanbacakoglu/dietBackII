package com.example.demo.repository;

import com.example.demo.entity.Randevu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RandevuRepository extends JpaRepository<Randevu, Long> {
    List<Randevu> findByHastaId(Long hastaId);
    List<Randevu> findByDiyetisyenId(Long diyetisyenId);
    List<Randevu> findByRandevuZamaniBetween(LocalDateTime baslangic, LocalDateTime bitis);
}
