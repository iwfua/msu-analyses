//package ru.banksystem.training.kostyaback.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import ru.banksystem.training.kostyaback.dto.LoginRequest;
//import ru.banksystem.training.kostyaback.dto.RegisterRequest;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//@ActiveProfiles("test")
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testRegister() throws Exception {
//        RegisterRequest registerRequest = new RegisterRequest("testuser", "test@example.com", "password123");
//
//        mockMvc.perform(post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(registerRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").exists())
//                .andExpect(jsonPath("$.user.username").value("testuser"))
//                .andExpect(jsonPath("$.user.email").value("test@example.com"));
//    }
//
//    @Test
//    void testLogin() throws Exception {
//        // Сначала регистрируем пользователя
//        RegisterRequest registerRequest = new RegisterRequest("logintest", "logintest@example.com", "password123");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(registerRequest)));
//
//        // Затем логинимся
//        LoginRequest loginRequest = new LoginRequest("logintest", "password123");
//
//        mockMvc.perform(post("/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").exists())
//                .andExpect(jsonPath("$.user.username").value("logintest"));
//    }
//
//    @Test
//    void testLoginWithInvalidCredentials() throws Exception {
//        LoginRequest loginRequest = new LoginRequest("nonexistent", "wrongpassword");
//
//        mockMvc.perform(post("/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().is5xxServerError());
//    }
//
//    @Test
//    void testRegisterWithExistingUsername() throws Exception {
//        // Регистрируем пользователя
//        RegisterRequest registerRequest = new RegisterRequest("duplicate", "duplicate1@example.com", "password123");
//        mockMvc.perform(post("/api/auth/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(registerRequest)));
//
//        // Пытаемся зарегистрировать с тем же username
//        RegisterRequest duplicateRequest = new RegisterRequest("duplicate", "duplicate2@example.com", "password456");
//
//        mockMvc.perform(post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(duplicateRequest)))
//                .andExpect(status().is5xxServerError());
//    }
//}