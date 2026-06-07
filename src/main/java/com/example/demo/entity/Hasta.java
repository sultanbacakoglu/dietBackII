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
    private String dogumTarihi;
    private String cinsiyet;
    private Double boy;
    private Double kilo;

    @Column(columnDefinition = "TEXT")
    private String sikayet;

    @Column(columnDefinition = "TEXT")
    private String notlar;

    @ManyToOne
    @JoinColumn(name = "diyetisyen_id")
    private Diyetisyen diyetisyen;
}
