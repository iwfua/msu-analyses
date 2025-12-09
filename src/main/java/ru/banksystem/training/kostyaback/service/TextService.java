package ru.banksystem.training.kostyaback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.banksystem.training.kostyaback.domain.Text;
import ru.banksystem.training.kostyaback.exception.NotFoundException;
import ru.banksystem.training.kostyaback.repository.TextRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TextService {

    private final TextRepository textRepository;

    public List<Text> findAll() {
        return textRepository.findAll();
    }

    public Text findTextById(String id) {
        return textRepository.findByIdentif(id).orElseThrow(() -> new NotFoundException("not found text with id=" + id));
    }

    public Text createTextById(Text text) {
        return textRepository.save(text);
    }

    public Text updateTextById(Text text) {
        Optional<Text> oldText = textRepository.findByIdentif(text.getIdentif());

        if (oldText.isEmpty()) {
            return textRepository.save(text);
        }

        text.setId(oldText.get().getId());
        return textRepository.save(text);
    }
}
