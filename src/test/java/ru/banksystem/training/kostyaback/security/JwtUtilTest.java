//package ru.banksystem.training.kostyaback.security;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class JwtUtilTest {
//
//    private JwtUtil jwtUtil;
//    private UserDetails userDetails;
//
//    @BeforeEach
//    void setUp() {
//        jwtUtil = new JwtUtil();
//        // Устанавливаем тестовые значения через рефлексию
//        ReflectionTestUtils.setField(jwtUtil, "secret", "5368566D597133743677397A24432646294A404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970337336763979244226452948404D");
//        ReflectionTestUtils.setField(jwtUtil, "jwtExpiration", 86400000L);
//
//        userDetails = User.builder()
//                .username("testuser")
//                .password("password")
//                .authorities(Collections.emptyList())
//                .build();
//    }
//
//    @Test
//    void testGenerateToken() {
//        String token = jwtUtil.generateToken(userDetails);
//
//        assertNotNull(token);
//        assertFalse(token.isEmpty());
//    }
//
//    @Test
//    void testExtractUsername() {
//        String token = jwtUtil.generateToken(userDetails);
//        String extractedUsername = jwtUtil.extractUsername(token);
//
//        assertEquals(userDetails.getUsername(), extractedUsername);
//    }
//
//    @Test
//    void testValidateToken() {
//        String token = jwtUtil.generateToken(userDetails);
//
//        assertTrue(jwtUtil.isTokenValid(token, userDetails));
//    }
//
//    @Test
//    void testValidateTokenWithWrongUser() {
//        String token = jwtUtil.generateToken(userDetails);
//
//        UserDetails otherUser = User.builder()
//                .username("otheruser")
//                .password("password")
//                .authorities(Collections.emptyList())
//                .build();
//
//        assertFalse(jwtUtil.isTokenValid(token, otherUser));
//    }
//}