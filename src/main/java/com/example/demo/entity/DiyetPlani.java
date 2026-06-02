package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "diyet_planlari")
@Data
public class DiyetPlani {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baslik;
    private String aciklama;
    private LocalDate baslangicTarihi;
    private LocalDate bitisTarihi;
    private Integer kaloriHedefi;

    @Column(columnDefinition = "TEXT")
    private String kahvalti;

    @Column(columnDefinition = "TEXT")
    private String ogleYemegi;

    @Column(columnDefinition = "TEXT")
    private String aksamYemegi;

    @Column(columnDefinition = "TEXT")
    private String araOgun;

    @Column(columnDefinition = "TEXT")
    private String notlar;

    private String durum;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private Hasta hasta;

    @ManyToOne
    @JoinColumn(name = "diyetisyen_id")
    private Diyetisyen diyetisyen;
}
