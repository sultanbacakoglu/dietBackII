package com.example.demo.security;

import com.example.demo.entity.Diyetisyen;
import com.example.demo.repository.DiyetisyenRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final DiyetisyenRepository diyetisyenRepository;

    public CustomUserDetailsService(DiyetisyenRepository diyetisyenRepository) {
        this.diyetisyenRepository = diyetisyenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String eposta) throws UsernameNotFoundException {
        Diyetisyen diyetisyen = diyetisyenRepository.findByEposta(eposta)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + eposta));

        return User.builder()
                .username(diyetisyen.getEposta())
                .password(diyetisyen.getSifre())
                .roles("USER")
                .build();
    }
}
