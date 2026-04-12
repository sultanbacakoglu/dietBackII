package com.example.demo.controller;

import com.example.demo.dto.HastaDTO;
import com.example.demo.service.HastaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hastalar")
@CrossOrigin(origins = "*")
public class HastaController {
    private final HastaService hastaService;

    public HastaController(HastaService hastaService) {
        this.hastaService = hastaService;
    }

    @GetMapping
    public List<HastaDTO> listele() {
        return hastaService.tumHastalariGetir();
    }

    @GetMapping("/diyetisyen/{diyetisyenId}")
    public List<HastaDTO> listeleByDiyetisyen(@PathVariable Long diyetisyenId) {
        return hastaService.hastalariGetir(diyetisyenId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HastaDTO> getir(@PathVariable Long id) {
        return hastaService.hastaGetir(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HastaDTO ekle(@RequestBody HastaDTO hasta) {
        return hastaService.hastaKaydet(hasta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HastaDTO> guncelle(@PathVariable Long id, @RequestBody HastaDTO hasta) {
        return hastaService.hastaGuncelle(id, hasta)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sil(@PathVariable Long id) {
        hastaService.hastaSil(id);
        return ResponseEntity.noContent().build();
    }
}
