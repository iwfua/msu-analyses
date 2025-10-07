package ru.banksystem.training.kostyaback.service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.banksystem.training.kostyaback.repository.TextRepository;

@Service
@RequiredArgsConstructor
public class TextService {

    private final TextRepository textRepository;

    public String getTextById(String id) {
        return null;
    }

    public String saveTextById(String text) {
        return null;
    }
}
