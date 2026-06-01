package com.example.demo.service;

import com.example.demo.dto.RandevuDTO;
import com.example.demo.entity.Diyetisyen;
import com.example.demo.entity.Hasta;
import com.example.demo.entity.Randevu;
import com.example.demo.repository.DiyetisyenRepository;
import com.example.demo.repository.HastaRepository;
import com.example.demo.repository.RandevuRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RandevuService {

    private final RandevuRepository randevuRepo;
    private final HastaRepository hastaRepo;
    private final DiyetisyenRepository diyetisyenRepo;

    public RandevuService(RandevuRepository randevuRepo,
                          HastaRepository hastaRepo,
                          DiyetisyenRepository diyetisyenRepo) {
        this.randevuRepo = randevuRepo;
        this.hastaRepo = hastaRepo;
        this.diyetisyenRepo = diyetisyenRepo;
    }

    public List<RandevuDTO> tumRandevulariGetir(String tarih) {
        List<Randevu> liste;
        if (tarih != null && !tarih.isBlank()) {
            LocalDate gun = LocalDate.parse(tarih);
            liste = randevuRepo.findByRandevuZamaniBetween(gun.atStartOfDay(), gun.atTime(LocalTime.MAX));
        } else {
            liste = randevuRepo.findAll();
        }
        return liste.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RandevuDTO> hastaRandevulariniGetir(Long hastaId) {
        return randevuRepo.findByHastaId(hastaId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<RandevuDTO> diyetisyenRandevulariniGetir(Long diyetisyenId) {
        return randevuRepo.findByDiyetisyenId(diyetisyenId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<RandevuDTO> randevuGetir(Long id) {
        return randevuRepo.findById(id).map(this::toDTO);
    }

    public RandevuDTO randevuOlustur(RandevuDTO dto, Long diyetisyenId) {
        Randevu randevu = toEntity(dto);

        if (diyetisyenId != null) {
            Diyetisyen diyetisyen = diyetisyenRepo.findById(diyetisyenId)
                    .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı"));
            randevu.setDiyetisyen(diyetisyen);
        }

        if (randevu.getDurum() == null) {
            randevu.setDurum("bekliyor");
        }

        return toDTO(randevuRepo.save(randevu));
    }

    public Optional<RandevuDTO> randevuGuncelle(Long id, RandevuDTO dto) {
        return randevuRepo.findById(id).map(randevu -> {
            if (dto.getDurum() != null) randevu.setDurum(dto.getDurum());
            if (dto.getTip() != null) randevu.setTip(dto.getTip());
            if (dto.getNotlar() != null) randevu.setNotlar(dto.getNotlar());

            if (dto.getTarih() != null && dto.getSaat() != null) {
                randevu.setRandevuZamani(parseZaman(dto.getTarih(), dto.getSaat()));
            }

            if (dto.getHastaId() != null) {
                Hasta hasta = hastaRepo.findById(dto.getHastaId())
                        .orElseThrow(() -> new RuntimeException("Hasta bulunamadı"));
                randevu.setHasta(hasta);
            }

            if (dto.getDiyetisyenId() != null) {
                Diyetisyen diyetisyen = diyetisyenRepo.findById(dto.getDiyetisyenId())
                        .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı"));
                randevu.setDiyetisyen(diyetisyen);
            }

            return toDTO(randevuRepo.save(randevu));
        });
    }

    public void randevuSil(Long id) {
        randevuRepo.deleteById(id);
    }

    private RandevuDTO toDTO(Randevu randevu) {
        RandevuDTO dto = new RandevuDTO();
        dto.setId(randevu.getId());
        dto.setTip(randevu.getTip());
        dto.setDurum(randevu.getDurum());
        dto.setNotlar(randevu.getNotlar());

        if (randevu.getRandevuZamani() != null) {
            dto.setTarih(randevu.getRandevuZamani().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            dto.setSaat(randevu.getRandevuZamani().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        if (randevu.getHasta() != null) {
            dto.setHastaId(randevu.getHasta().getId());
            dto.setHastaAdSoyad(randevu.getHasta().getAdSoyad());
        }

        if (randevu.getDiyetisyen() != null) {
            dto.setDiyetisyenId(randevu.getDiyetisyen().getId());
            dto.setDiyetisyenAdSoyad(randevu.getDiyetisyen().getAdSoyad());
        }

        return dto;
    }

    private Randevu toEntity(RandevuDTO dto) {
        Randevu randevu = new Randevu();
        randevu.setTip(dto.getTip());
        randevu.setDurum(dto.getDurum());
        randevu.setNotlar(dto.getNotlar());

        if (dto.getTarih() != null && dto.getSaat() != null) {
            randevu.setRandevuZamani(parseZaman(dto.getTarih(), dto.getSaat()));
        }

        if (dto.getHastaId() != null) {
            Hasta hasta = hastaRepo.findById(dto.getHastaId())
                    .orElseThrow(() -> new RuntimeException("Hasta bulunamadı: " + dto.getHastaId()));
            randevu.setHasta(hasta);
        }

        return randevu;
    }

    private LocalDateTime parseZaman(String tarih, String saat) {
        return LocalDateTime.of(
                LocalDate.parse(tarih, DateTimeFormatter.ISO_LOCAL_DATE),
                LocalTime.parse(saat, DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
