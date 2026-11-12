package org.example.demo.controller;

import org.example.demo.entity.Suti;
import org.example.demo.repository.ArRepository;
import org.example.demo.repository.SutiRepository;
import org.example.demo.repository.TartalomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AdatbazisController {

    private final SutiRepository sutiRepository;
    private final TartalomRepository tartalomRepository;
    private final ArRepository arRepository;

    public AdatbazisController(SutiRepository sutiRepository,
                               TartalomRepository tartalomRepository,
                               ArRepository arRepository) {
        this.sutiRepository = sutiRepository;
        this.tartalomRepository = tartalomRepository;
        this.arRepository = arRepository;
    }

    @GetMapping("/adatbazis")
    public String adatbazis(Model model) {
        List<Suti> sutik = sutiRepository.findAll();

        Map<Integer, String> sutiNevek = sutik.stream()
                .collect(Collectors.toMap(Suti::getId, Suti::getNev));

        model.addAttribute("title", "Adatbázis");
        model.addAttribute("active", "adatbazis");
        model.addAttribute("sutik", sutik);
        model.addAttribute("tartalmak", tartalomRepository.findAll());
        model.addAttribute("arak", arRepository.findAll());
        model.addAttribute("sutiNevek", sutiNevek);
        return "adatbazis";
    }
}
