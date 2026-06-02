package com.example.demo.service;

import com.example.demo.dto.DiyetPlaniDTO;
import com.example.demo.entity.Diyetisyen;
import com.example.demo.entity.DiyetPlani;
import com.example.demo.entity.Hasta;
import com.example.demo.repository.DiyetisyenRepository;
import com.example.demo.repository.DiyetPlaniRepository;
import com.example.demo.repository.HastaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiyetPlaniService {

    private final DiyetPlaniRepository diyetPlaniRepo;
    private final HastaRepository hastaRepo;
    private final DiyetisyenRepository diyetisyenRepo;

    public DiyetPlaniService(DiyetPlaniRepository diyetPlaniRepo,
                             HastaRepository hastaRepo,
                             DiyetisyenRepository diyetisyenRepo) {
        this.diyetPlaniRepo = diyetPlaniRepo;
        this.hastaRepo = hastaRepo;
        this.diyetisyenRepo = diyetisyenRepo;
    }

    public List<DiyetPlaniDTO> tumPlanlarıGetir() {
        return diyetPlaniRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<DiyetPlaniDTO> hastaPlanlariniGetir(Long hastaId) {
        return diyetPlaniRepo.findByHastaId(hastaId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<DiyetPlaniDTO> diyetisyenPlanlariniGetir(Long diyetisyenId) {
        return diyetPlaniRepo.findByDiyetisyenId(diyetisyenId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<DiyetPlaniDTO> planGetir(Long id) {
        return diyetPlaniRepo.findById(id).map(this::toDTO);
    }

    public DiyetPlaniDTO planOlustur(DiyetPlaniDTO dto, Long diyetisyenId) {
        DiyetPlani plan = toEntity(dto);

        if (diyetisyenId != null) {
            Diyetisyen d = diyetisyenRepo.findById(diyetisyenId)
                    .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı"));
            plan.setDiyetisyen(d);
        }

        if (plan.getDurum() == null) {
            plan.setDurum("aktif");
        }

        return toDTO(diyetPlaniRepo.save(plan));
    }

    public Optional<DiyetPlaniDTO> planGuncelle(Long id, DiyetPlaniDTO dto) {
        return diyetPlaniRepo.findById(id).map(plan -> {
            if (dto.getBaslik() != null) plan.setBaslik(dto.getBaslik());
            if (dto.getAciklama() != null) plan.setAciklama(dto.getAciklama());
            if (dto.getKaloriHedefi() != null) plan.setKaloriHedefi(dto.getKaloriHedefi());
            if (dto.getKahvalti() != null) plan.setKahvalti(dto.getKahvalti());
            if (dto.getOgleYemegi() != null) plan.setOgleYemegi(dto.getOgleYemegi());
            if (dto.getAksamYemegi() != null) plan.setAksamYemegi(dto.getAksamYemegi());
            if (dto.getAraOgun() != null) plan.setAraOgun(dto.getAraOgun());
            if (dto.getNotlar() != null) plan.setNotlar(dto.getNotlar());
            if (dto.getDurum() != null) plan.setDurum(dto.getDurum());

            if (dto.getBaslangicTarihi() != null)
                plan.setBaslangicTarihi(LocalDate.parse(dto.getBaslangicTarihi()));
            if (dto.getBitisTarihi() != null)
                plan.setBitisTarihi(LocalDate.parse(dto.getBitisTarihi()));

            if (dto.getHastaId() != null) {
                Hasta hasta = hastaRepo.findById(dto.getHastaId())
                        .orElseThrow(() -> new RuntimeException("Hasta bulunamadı"));
                plan.setHasta(hasta);
            }

            return toDTO(diyetPlaniRepo.save(plan));
        });
    }

    public void planSil(Long id) {
        diyetPlaniRepo.deleteById(id);
    }

    private DiyetPlaniDTO toDTO(DiyetPlani plan) {
        DiyetPlaniDTO dto = new DiyetPlaniDTO();
        dto.setId(plan.getId());
        dto.setBaslik(plan.getBaslik());
        dto.setAciklama(plan.getAciklama());
        dto.setKaloriHedefi(plan.getKaloriHedefi());
        dto.setKahvalti(plan.getKahvalti());
        dto.setOgleYemegi(plan.getOgleYemegi());
        dto.setAksamYemegi(plan.getAksamYemegi());
        dto.setAraOgun(plan.getAraOgun());
        dto.setNotlar(plan.getNotlar());
        dto.setDurum(plan.getDurum());

        if (plan.getBaslangicTarihi() != null)
            dto.setBaslangicTarihi(plan.getBaslangicTarihi().format(DateTimeFormatter.ISO_LOCAL_DATE));
        if (plan.getBitisTarihi() != null)
            dto.setBitisTarihi(plan.getBitisTarihi().format(DateTimeFormatter.ISO_LOCAL_DATE));

        if (plan.getHasta() != null) {
            dto.setHastaId(plan.getHasta().getId());
            dto.setHastaAdSoyad(plan.getHasta().getAdSoyad());
        }
        if (plan.getDiyetisyen() != null) {
            dto.setDiyetisyenId(plan.getDiyetisyen().getId());
            dto.setDiyetisyenAdSoyad(plan.getDiyetisyen().getAdSoyad());
        }

        return dto;
    }

    private DiyetPlani toEntity(DiyetPlaniDTO dto) {
        DiyetPlani plan = new DiyetPlani();
        plan.setBaslik(dto.getBaslik());
        plan.setAciklama(dto.getAciklama());
        plan.setKaloriHedefi(dto.getKaloriHedefi());
        plan.setKahvalti(dto.getKahvalti());
        plan.setOgleYemegi(dto.getOgleYemegi());
        plan.setAksamYemegi(dto.getAksamYemegi());
        plan.setAraOgun(dto.getAraOgun());
        plan.setNotlar(dto.getNotlar());
        plan.setDurum(dto.getDurum());

        if (dto.getBaslangicTarihi() != null)
            plan.setBaslangicTarihi(LocalDate.parse(dto.getBaslangicTarihi()));
        if (dto.getBitisTarihi() != null)
            plan.setBitisTarihi(LocalDate.parse(dto.getBitisTarihi()));

        if (dto.getHastaId() != null) {
            Hasta hasta = hastaRepo.findById(dto.getHastaId())
                    .orElseThrow(() -> new RuntimeException("Hasta bulunamadı: " + dto.getHastaId()));
            plan.setHasta(hasta);
        }

        return plan;
    }
}
