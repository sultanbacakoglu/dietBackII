package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.ProfilGuncelleRequest;
import com.example.demo.entity.Diyetisyen;
import com.example.demo.repository.DiyetisyenRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final DiyetisyenRepository diyetisyenRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil,
                          DiyetisyenRepository diyetisyenRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.diyetisyenRepository = diyetisyenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEposta(), request.getSifre())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEposta());
        String token = jwtUtil.generateToken(userDetails);
        Optional<Diyetisyen> diyetisyen = diyetisyenRepository.findByEposta(request.getEposta());
        return diyetisyen.map(d -> ResponseEntity.ok(
            new LoginResponse(token, d.getId(), d.getAdSoyad(), d.getEposta())
        )).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(@AuthenticationPrincipal UserDetails userDetails) {
        return diyetisyenRepository.findByEposta(userDetails.getUsername())
            .map(d -> ResponseEntity.ok(new LoginResponse(null, d.getId(), d.getAdSoyad(), d.getEposta())))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/me")
    public ResponseEntity<?> guncelle(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ProfilGuncelleRequest req) {

        Optional<Diyetisyen> opt = diyetisyenRepository.findByEposta(userDetails.getUsername());
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Diyetisyen d = opt.get();

        if (req.getAdSoyad() != null && !req.getAdSoyad().isBlank()) {
            d.setAdSoyad(req.getAdSoyad().trim());
        }

        if (req.getYeniSifre() != null && !req.getYeniSifre().isBlank()) {
            if (req.getMevcutSifre() == null || !passwordEncoder.matches(req.getMevcutSifre(), d.getSifre())) {
                return ResponseEntity.badRequest().body("Mevcut şifre hatalı");
            }
            d.setSifre(passwordEncoder.encode(req.getYeniSifre()));
        }

        diyetisyenRepository.save(d);
        return ResponseEntity.ok(new LoginResponse(null, d.getId(), d.getAdSoyad(), d.getEposta()));
    }
}
