package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiyetPlaniDTO {
    private Long id;
    private String baslik;
    private String aciklama;
    private String baslangicTarihi;
    private String bitisTarihi;
    private Integer kaloriHedefi;
    private String kahvalti;
    private String ogleYemegi;
    private String aksamYemegi;
    private String araOgun;
    private String notlar;
    private String durum;
    private Long hastaId;
    private String hastaAdSoyad;
    private Long diyetisyenId;
    private String diyetisyenAdSoyad;
}
