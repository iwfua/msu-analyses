package ru.banksystem.training.kostyaback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KostyaBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(KostyaBackApplication.class, args);
    }

}
