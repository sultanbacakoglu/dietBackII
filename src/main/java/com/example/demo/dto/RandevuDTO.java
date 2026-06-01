package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RandevuDTO {
    private Long id;
    private String tarih;
    private String saat;
    private String tip;
    private String durum;
    private String notlar;
    private Long hastaId;
    private String hastaAdSoyad;
    private Long diyetisyenId;
    private String diyetisyenAdSoyad;
}
