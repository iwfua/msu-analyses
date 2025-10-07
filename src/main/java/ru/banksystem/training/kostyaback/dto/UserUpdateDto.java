package ru.banksystem.training.kostyaback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserUpdateDto {

    @NotBlank
    private Long id;

    @NotBlank
    @Size(min = 1, max = 100)
    private String  firstName;

    @NotBlank
    @Size(min = 1, max = 100)
    private String lastName;

    @Email
    @NotBlank
    private String email;

}
