package ru.banksystem.training.kostyaback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на регистрацию нового пользователя")
public class RegisterRequest {
    
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 50, message = "Имя пользователя должно содержать от 3 до 50 символов")
    @Schema(description = "Имя пользователя (3-50 символов)", example = "newuser", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    @Schema(description = "Email адрес пользователя", example = "newuser@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    @Schema(description = "Пароль (минимум 6 символов)", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}