package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hastalar")
@Data
public class Hasta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adSoyad;
    private String eposta;
    private String telefon;

    @ManyToOne
    @JoinColumn(name = "diyetisyen_id")
    private Diyetisyen diyetisyen;
}