package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "diyetisyenler")
@Data
public class Diyetisyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adSoyad;
    private String eposta;
    private String sifre;

    @OneToMany(mappedBy = "diyetisyen")
    @JsonIgnore
    private List<Hasta> hastalar;
}