package ru.banksystem.training.kostyaback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.banksystem.training.kostyaback.domain.Text;
import ru.banksystem.training.kostyaback.service.TextService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/text")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TextOnPageController {

    @Autowired
    private final TextService service;

    @GetMapping
    public List<Text> findAll() {
        log.info("Пришел GET /findAll");

        return service.findAll();
    }

    @GetMapping("/{id}")
    public Text findTextById(@PathVariable String id) {
        log.info("Пришел GET /text/{}", id);
        return service.findTextById(id);
    }

    @PostMapping()
    public Text addText(@RequestBody Text text) {
        return service.createTextById(text);
    }

    @PatchMapping("/update")
    public Text updateText(@RequestBody Text text) {
        return service.updateTextById(text);
    }
}
