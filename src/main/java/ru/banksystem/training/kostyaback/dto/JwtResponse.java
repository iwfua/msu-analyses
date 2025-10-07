package ru.banksystem.training.kostyaback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с JWT токенами после аутентификации")
public class JwtResponse {
    
    @Schema(description = "Access Token для доступа к API (живет 15 минут)", 
            example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzOTY0MDgwMCwiZXhwIjoxNjM5NjQxNzAwfQ...")
    private String accessToken;
    
    @Schema(description = "Refresh Token для обновления Access Token (живет 7 дней)", 
            example = "550e8400-e29b-41d4-a716-446655440000")
    private String refreshToken;
    
    @Schema(description = "Тип токена", example = "Bearer", defaultValue = "Bearer")
    private String type = "Bearer";
    
    @Schema(description = "Информация о пользователе")
    private UserDto user;
    
    @Schema(description = "Время жизни Access Token в секундах", example = "900")
    private Long expiresIn;
    
    public JwtResponse(String accessToken, String refreshToken, UserDto user, Long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expiresIn = expiresIn;
    }
}