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
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class UserController {

    private final UserService service;

    public UserController(UserService userService) {
        this.service = userService;
    }

    @RequestMapping("/update/{id}")
    @PostMapping
    public User updateUser(@PathVariable String id,
                           @RequestBody @Valid UserUpdateDto userDto) {
        log.info("updateUser: id={}, user={}", id, userDto);
        return service.updateUserById(Long.parseLong(id), userDto);
    }

    @RequestMapping("/delte/{id}")
    @DeleteMapping
    public void deleteUserById(@PathVariable String id) {
        log.info("Прилетел DELETE для user with id={}", id);
        service.deleteUserById(id);
    }

    @RequestMapping("/delete/{username}")
    public void deleteUserByUsername(@PathVariable String username) {
        service.deleteUserByUsername(username);
    }
}
