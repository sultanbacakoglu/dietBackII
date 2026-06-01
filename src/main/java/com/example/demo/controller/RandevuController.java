package com.example.demo.controller;

import com.example.demo.dto.RandevuDTO;
import com.example.demo.entity.Diyetisyen;
import com.example.demo.repository.DiyetisyenRepository;
import com.example.demo.service.RandevuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/randevular")
public class RandevuController {

    private final RandevuService randevuService;
    private final DiyetisyenRepository diyetisyenRepo;

    public RandevuController(RandevuService randevuService, DiyetisyenRepository diyetisyenRepo) {
        this.randevuService = randevuService;
        this.diyetisyenRepo = diyetisyenRepo;
    }

    @GetMapping
    public ResponseEntity<List<RandevuDTO>> tumRandevular(@RequestParam(required = false) String tarih) {
        return ResponseEntity.ok(randevuService.tumRandevulariGetir(tarih));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RandevuDTO> randevuGetir(@PathVariable Long id) {
        return randevuService.randevuGetir(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hasta/{hastaId}")
    public ResponseEntity<List<RandevuDTO>> hastaRandevulari(@PathVariable Long hastaId) {
        return ResponseEntity.ok(randevuService.hastaRandevulariniGetir(hastaId));
    }

    @GetMapping("/diyetisyen/{diyetisyenId}")
    public ResponseEntity<List<RandevuDTO>> diyetisyenRandevulari(@PathVariable Long diyetisyenId) {
        return ResponseEntity.ok(randevuService.diyetisyenRandevulariniGetir(diyetisyenId));
    }

    @PostMapping
    public ResponseEntity<RandevuDTO> randevuOlustur(
            @RequestBody RandevuDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long diyetisyenId = getDiyetisyenId(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(randevuService.randevuOlustur(dto, diyetisyenId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RandevuDTO> randevuGuncelle(@PathVariable Long id, @RequestBody RandevuDTO dto) {
        return randevuService.randevuGuncelle(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> randevuSil(@PathVariable Long id) {
        randevuService.randevuSil(id);
        return ResponseEntity.noContent().build();
    }

    private Long getDiyetisyenId(UserDetails userDetails) {
        if (userDetails == null) return null;
        return diyetisyenRepo.findByEposta(userDetails.getUsername())
                .map(Diyetisyen::getId)
                .orElse(null);
    }
}
