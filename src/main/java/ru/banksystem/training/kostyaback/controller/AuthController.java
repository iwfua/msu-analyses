package ru.banksystem.training.kostyaback.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.banksystem.training.kostyaback.domain.RefreshToken;
import ru.banksystem.training.kostyaback.domain.Role;
import ru.banksystem.training.kostyaback.domain.User;
import ru.banksystem.training.kostyaback.dto.JwtResponse;
import ru.banksystem.training.kostyaback.dto.LoginRequest;
import ru.banksystem.training.kostyaback.dto.RefreshTokenRequest;
import ru.banksystem.training.kostyaback.dto.RegisterRequest;
import ru.banksystem.training.kostyaback.dto.UserDto;
import ru.banksystem.training.kostyaback.exception.NotFoundException;
import ru.banksystem.training.kostyaback.mapper.UserDtoMapper;
import ru.banksystem.training.kostyaback.security.JwtUtil;
import ru.banksystem.training.kostyaback.service.RefreshTokenService;
import ru.banksystem.training.kostyaback.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserDtoMapper userMapper;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            
            UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
            User user = userService.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            
            String accessToken = jwtUtil.generateToken(userDetails);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
            
            UserDto userDto = userMapper.toDto(user);
            
            return ResponseEntity.ok(new JwtResponse(
                    accessToken, 
                    refreshToken.getToken(), 
                    userDto, 
                    900L // 15 minutes in seconds
            ));
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка аутентификации: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.createUser(
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    Role.USER
            );
            
            String accessToken = jwtUtil.generateToken(user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
            UserDto userDto = userMapper.toDto(user);
            
            return ResponseEntity.ok(new JwtResponse(
                    accessToken, 
                    refreshToken.getToken(), 
                    userDto, 
                    900L // 15 minutes in seconds
            ));
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка регистрации: " + e.getMessage());
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            
            UserDto userDto = userMapper.toDto(user);
            return ResponseEntity.ok(userDto);
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка получения пользователя: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            String requestRefreshToken = request.getRefreshToken();
            
            RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .orElseThrow(() -> new RuntimeException("Неверный refresh token"));
            
            User user = refreshToken.getUser();
            String newAccessToken = jwtUtil.generateToken(user);
            
            UserDto userDto = userMapper.toDto(user);
            
            return ResponseEntity.ok(new JwtResponse(
                    newAccessToken,
                    requestRefreshToken,
                    userDto,
                    900L
            ));
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка обновления токена: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            String requestRefreshToken = request.getRefreshToken();
            
            RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                    .orElseThrow(() -> new RuntimeException("Неверный refresh token"));
            
            refreshTokenService.deleteByUser(refreshToken.getUser());
            
            return ResponseEntity.ok(Map.of("message", "Выход выполнен успешно"));
            
        } catch (Exception e) {
            throw new RuntimeException("Ошибка выхода: " + e.getMessage());
        }
    }
}
