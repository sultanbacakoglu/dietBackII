package com.example.demo.service;

import com.example.demo.entity.Hasta;
import com.example.demo.repository.HastaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HastaService {
    @Autowired
    private HastaRepository hastaRepo;

    public List<Hasta> tumHastalariGetir() { return hastaRepo.findAll(); }
    public Hasta hastaKaydet(Hasta hasta) { return hastaRepo.save(hasta); }
    public void hastaSil(Long id) { hastaRepo.deleteById(id); }
}