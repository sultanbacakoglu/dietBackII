package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "randevular")
@Data
public class Randevu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime randevuZamani;
    private String notlar;

    @ManyToOne
    @JoinColumn(name = "diyetisyen_id")
    private Diyetisyen diyetisyen;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private Hasta hasta;
}