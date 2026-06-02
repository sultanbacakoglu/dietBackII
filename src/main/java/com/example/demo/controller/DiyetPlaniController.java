package com.example.demo.controller;

import com.example.demo.dto.DiyetPlaniDTO;
import com.example.demo.entity.Diyetisyen;
import com.example.demo.repository.DiyetisyenRepository;
import com.example.demo.service.DiyetPlaniService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diyet-planlari")
public class DiyetPlaniController {

    private final DiyetPlaniService service;
    private final DiyetisyenRepository diyetisyenRepo;

    public DiyetPlaniController(DiyetPlaniService service, DiyetisyenRepository diyetisyenRepo) {
        this.service = service;
        this.diyetisyenRepo = diyetisyenRepo;
    }

    @GetMapping
    public ResponseEntity<List<DiyetPlaniDTO>> tumPlanlar() {
        return ResponseEntity.ok(service.tumPlanlarıGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiyetPlaniDTO> planGetir(@PathVariable Long id) {
        return service.planGetir(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hasta/{hastaId}")
    public ResponseEntity<List<DiyetPlaniDTO>> hastaPlanları(@PathVariable Long hastaId) {
        return ResponseEntity.ok(service.hastaPlanlariniGetir(hastaId));
    }

    @GetMapping("/diyetisyen/{diyetisyenId}")
    public ResponseEntity<List<DiyetPlaniDTO>> diyetisyenPlanları(@PathVariable Long diyetisyenId) {
        return ResponseEntity.ok(service.diyetisyenPlanlariniGetir(diyetisyenId));
    }

    @PostMapping
    public ResponseEntity<DiyetPlaniDTO> planOlustur(
            @RequestBody DiyetPlaniDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long diyetisyenId = getDiyetisyenId(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.planOlustur(dto, diyetisyenId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiyetPlaniDTO> planGuncelle(
            @PathVariable Long id, @RequestBody DiyetPlaniDTO dto) {
        return service.planGuncelle(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> planSil(@PathVariable Long id) {
        service.planSil(id);
        return ResponseEntity.noContent().build();
    }

    private Long getDiyetisyenId(UserDetails userDetails) {
        if (userDetails == null) return null;
        return diyetisyenRepo.findByEposta(userDetails.getUsername())
                .map(Diyetisyen::getId)
                .orElse(null);
    }
}
