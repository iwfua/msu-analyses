package ru.banksystem.training.kostyaback.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TextCreateDto {

    @NotBlank
    private String identif;

    @NotBlank
    private String text;
}
