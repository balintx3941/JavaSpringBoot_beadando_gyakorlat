package org.example.demo.config;

import org.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                // látogató (nem bejelentkezett): ezeket látja
                .requestMatchers("/", "/adatbazis", "/kapcsolat", "/diagram", "/rest",
                        "/login", "/register", "/api/**",
                        "/css/**", "/js/**", "/img/**", "/lib/**", "/scss/**").permitAll()
                // regisztrált látogató és admin
                .requestMatchers("/uzenetek").hasAnyRole("USER", "ADMIN")
                // csak admin
                .requestMatchers("/crud/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            // A REST API-t Postman/cURL is hívja CSRF-token nélkül
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                // jogosulatlan oldal esetén a főoldalra irányít (a kontextus-útvonalat is figyelembe véve)
                .accessDeniedHandler((request, response, e) ->
                        response.sendRedirect(request.getContextPath() + "/"))
            );
        return http.build();
    }
}
