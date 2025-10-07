package ru.banksystem.training.kostyaback.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.banksystem.training.kostyaback.domain.Role;
import ru.banksystem.training.kostyaback.service.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        if (!userService.existsByUsername("admin")) {
            userService.createUser("admin", "admin@example.com", "admin", Role.ADMIN);
            log.info("Создан администратор: admin / admin");
        }
        
        if (!userService.existsByUsername("user")) {
            userService.createUser("user", "user@example.com", "user", Role.USER);
            log.info("Создан пользователь: user / user");
        }
    }
}