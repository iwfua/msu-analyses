package ru.banksystem.training.kostyaback.mapper;

import org.springframework.stereotype.Component;
import ru.banksystem.training.kostyaback.domain.User;
import ru.banksystem.training.kostyaback.dto.UserDto;
import ru.banksystem.training.kostyaback.dto.UserUpdateDto;

@Component
public class UserDtoMapper {
    
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();
    }
}
