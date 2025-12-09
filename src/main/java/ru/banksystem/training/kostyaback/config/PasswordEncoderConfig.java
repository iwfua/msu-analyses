package ru.banksystem.training.kostyaback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PasswordEncoderConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Strength 8 дает ~50-70мс вместо 200-300мс при strength 10
        // Для production можно использовать 8-9, это все еще безопасно
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}