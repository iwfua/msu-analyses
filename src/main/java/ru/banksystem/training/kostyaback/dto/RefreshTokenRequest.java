package ru.banksystem.training.kostyaback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос с Refresh Token для обновления или выхода")
public class RefreshTokenRequest {
    
    @NotBlank(message = "Refresh token не может быть пустым")
    @Schema(description = "Refresh Token, полученный при логине", 
            example = "550e8400-e29b-41d4-a716-446655440000", 
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String refreshToken;
}