package ru.banksystem.training.kostyaback.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.banksystem.training.kostyaback.domain.User;
import ru.banksystem.training.kostyaback.dto.UserUpdateDto;
import ru.banksystem.training.kostyaback.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @PostMapping(value = "/update/{id}")
    public User updateUser(@PathVariable String id,
                           @RequestBody @Valid UserUpdateDto userDto) {
        log.info("Прилетел запрос на UPDATE: id={}, user={}", id, userDto);
        User resp = service.updateUserById(Long.parseLong(id), userDto);
        log.info("Ответ на запрос: id={}, user={}", id, resp);
        return resp;
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteUserById(@PathVariable String id) {
        log.info("Прилетел DELETE для user with id={}", id);
        service.deleteUserById(id);
        log.info("Запрос DELETE отработал для  user with id={}", id);
    }

    @DeleteMapping("/delete/{username}")
    public void deleteUserByUsername(@PathVariable String username) {
        log.info("Прилетел DELETE для user with username={}", username);
        service.deleteUserByUsername(username);
        log.info("Запрос DELETE отработал для  user with username={}", username);
    }
}
