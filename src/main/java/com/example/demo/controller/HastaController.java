package com.example.demo.controller;

import com.example.demo.entity.Hasta;
import com.example.demo.service.HastaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hastalar")
@CrossOrigin(origins = "*")
public class HastaController {
    @Autowired
    private HastaService hastaService;

    @GetMapping
    public List<Hasta> listele() { return hastaService.tumHastalariGetir(); }

    @PostMapping
    public Hasta ekle(@RequestBody Hasta hasta) { return hastaService.hastaKaydet(hasta); }

    @DeleteMapping("/{id}")
    public void sil(@PathVariable Long id) { hastaService.hastaSil(id); }
}