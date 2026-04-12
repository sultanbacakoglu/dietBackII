package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HastaDTO {
    private Long id;
    private String adSoyad;
    private String eposta;
    private String telefon;
    private Long diyetisyenId;
}
