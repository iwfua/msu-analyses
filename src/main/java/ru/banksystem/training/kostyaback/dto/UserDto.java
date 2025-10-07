package ru.banksystem.training.kostyaback.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.banksystem.training.kostyaback.domain.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для передачи информации о пользователе")
public class UserDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "admin")
    private String username;

    private String firstName;

    private String lastName;

    @Schema(description = "Email адрес пользователя", example = "admin@example.com")
    private String email;
    
    @Schema(description = "Роль пользователя в системе", example = "ADMIN")
    private Role role;
}
