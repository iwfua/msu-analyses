package ru.banksystem.training.kostyaback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TextDto {
    private String id;

    @Size(max = 70)
    @NotBlank
    private String key;

    @Size(max = 255)
    @NotBlank
    private String value;
}
