package com.example.demo.service;

import com.example.demo.dto.HastaDTO;
import com.example.demo.entity.Diyetisyen;
import com.example.demo.entity.Hasta;
import com.example.demo.repository.DiyetisyenRepository;
import com.example.demo.repository.HastaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HastaService {
    private final HastaRepository hastaRepo;
    private final DiyetisyenRepository diyetisyenRepo;

    public HastaService(HastaRepository hastaRepo, DiyetisyenRepository diyetisyenRepo) {
        this.hastaRepo = hastaRepo;
        this.diyetisyenRepo = diyetisyenRepo;
    }

    public List<HastaDTO> tumHastalariGetir() {
        return hastaRepo.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public List<HastaDTO> hastalariGetir(Long diyetisyenId) {
        return hastaRepo.findByDiyetisyenId(diyetisyenId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public Optional<HastaDTO> hastaGetir(Long id) {
        return hastaRepo.findById(id).map(this::toDTO);
    }

    public HastaDTO hastaKaydet(HastaDTO dto) {
        Hasta hasta = toEntity(dto);
        return toDTO(hastaRepo.save(hasta));
    }

    public Optional<HastaDTO> hastaGuncelle(Long id, HastaDTO dto) {
        return hastaRepo.findById(id).map(hasta -> {
            hasta.setAdSoyad(dto.getAdSoyad());
            hasta.setEposta(dto.getEposta());
            hasta.setTelefon(dto.getTelefon());
            return toDTO(hastaRepo.save(hasta));
        });
    }

    public void hastaSil(Long id) {
        hastaRepo.deleteById(id);
    }

    private HastaDTO toDTO(Hasta hasta) {
        return new HastaDTO(
            hasta.getId(),
            hasta.getAdSoyad(),
            hasta.getEposta(),
            hasta.getTelefon(),
            hasta.getDiyetisyen() != null ? hasta.getDiyetisyen().getId() : null
        );
    }

    private Hasta toEntity(HastaDTO dto) {
        Hasta hasta = new Hasta();
        hasta.setId(dto.getId());
        hasta.setAdSoyad(dto.getAdSoyad());
        hasta.setEposta(dto.getEposta());
        hasta.setTelefon(dto.getTelefon());
        if (dto.getDiyetisyenId() != null) {
            Diyetisyen diyetisyen = diyetisyenRepo.findById(dto.getDiyetisyenId())
                .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı"));
            hasta.setDiyetisyen(diyetisyen);
        }
        return hasta;
    }
}
