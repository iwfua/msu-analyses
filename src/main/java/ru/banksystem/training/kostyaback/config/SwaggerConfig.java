package ru.banksystem.training.kostyaback.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Kostya-Back JWT Authentication API",
                description = """
                        RESTful API для аутентификации с использованием JWT токенов.
                        
                        ## Особенности:
                        - 🔐 JWT аутентификация с Access и Refresh токенами
                        - 👤 Управление пользователями (регистрация, логин)
                        - 🔄 Автоматическое обновление токенов
                        - 🚪 Безопасный выход из системы
                        
                        ## Система токенов:
                        - **Access Token**: 15 минут, для доступа к API
                        - **Refresh Token**: 7 дней, для обновления Access Token
                        
                        ## Использование:
                        1. Зарегистрируйтесь или войдите для получения токенов
                        2. Используйте Access Token в заголовке Authorization
                        3. Обновляйте токен через /refresh при истечении
                        4. Выйдите через /logout для invalidации токенов
                        """,
                version = "1.0.0",
                contact = @Contact(
                        name = "Bank-System Training Team",
                        email = "support@bank-system.training"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development Server"),
                @Server(url = "https://api.bank-system.training", description = "Production Server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Введите JWT Access Token, полученный при логине или обновлении"
)
public class SwaggerConfig {
}

