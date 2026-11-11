package org.example.demo.controller;

import org.example.demo.entity.User;
import org.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controller2 {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Controller2(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "Bejelentkezés");
        model.addAttribute("active", "login");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("title", "Regisztráció");
        model.addAttribute("active", "register");
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        model.addAttribute("title", "Regisztráció");
        model.addAttribute("active", "register");
        if (username.isBlank() || password.length() < 4) {
            model.addAttribute("error", "A felhasználónév nem lehet üres, a jelszó legalább 4 karakter legyen!");
            return "register";
        }
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Ez a felhasználónév már foglalt!");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);
        return "redirect:/login?registered";
    }
}
