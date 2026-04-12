package com.example.demo.component;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DiyetisyenRepository diyetisyenRepo;
    private final HastaRepository hastaRepo;
    private final RandevuRepository randevuRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(DiyetisyenRepository d, HastaRepository h, RandevuRepository r, PasswordEncoder passwordEncoder) {
        this.diyetisyenRepo = d;
        this.hastaRepo = h;
        this.randevuRepo = r;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (diyetisyenRepo.count() == 0) {
            System.out.println(">> Veritabanı başlatılıyor...");
            initData();
        }
    }

    private void initData() {
        Diyetisyen d1 = new Diyetisyen();
        d1.setAdSoyad("Dyt. İbrahim Meral");
        d1.setEposta("ibrahim@test.com");
        d1.setSifre(passwordEncoder.encode("123456"));
        diyetisyenRepo.save(d1);

        Hasta h1 = new Hasta();
        h1.setAdSoyad("Ahmet Yılmaz");
        h1.setEposta("ahmet@mail.com");
        h1.setTelefon("05551112233");
        h1.setDiyetisyen(d1);

        Hasta h2 = new Hasta();
        h2.setAdSoyad("Ayşe Demir");
        h2.setEposta("ayse@mail.com");
        h2.setTelefon("05559998877");
        h2.setDiyetisyen(d1);

        hastaRepo.saveAll(List.of(h1, h2));

        Randevu r1 = new Randevu();
        r1.setRandevuZamani(LocalDateTime.now().plusHours(2));
        r1.setNotlar("Kilo takibi ve yağ ölçümü");
        r1.setDiyetisyen(d1);
        r1.setHasta(h1);

        Randevu r2 = new Randevu();
        r2.setRandevuZamani(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));
        r2.setNotlar("Yeni beslenme programı oluşturma");
        r2.setDiyetisyen(d1);
        r2.setHasta(h2);

        randevuRepo.saveAll(List.of(r1, r2));

        System.out.println(">> Veritabanı başarıyla örnek verilerle dolduruldu!");
    }
}
