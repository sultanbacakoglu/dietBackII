package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.Diyetisyen;
import com.example.demo.repository.DiyetisyenRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    public AuthController(AuthenticationManager authenticationManager,
                         UserDetailsService userDetailsService,
                         JwtUtil jwtUtil,
                         DiyetisyenRepository diyetisyenRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.diyetisyenRepository = diyetisyenRepository;
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
}
