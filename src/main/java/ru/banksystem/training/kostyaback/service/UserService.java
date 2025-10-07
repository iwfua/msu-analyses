package ru.banksystem.training.kostyaback.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.banksystem.training.kostyaback.domain.RefreshToken;
import ru.banksystem.training.kostyaback.domain.Role;
import ru.banksystem.training.kostyaback.domain.User;
import ru.banksystem.training.kostyaback.dto.UserUpdateDto;
import ru.banksystem.training.kostyaback.exception.NotFoundException;
import ru.banksystem.training.kostyaback.repository.RefreshTokenRepository;
import ru.banksystem.training.kostyaback.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User createUser(String username, String email, String password, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Пользователь с таким именем уже существует: " + username);
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Пользователь с таким email уже существует: " + email);
        }

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        return save(user);
    }

    public User updateUserById(Long id, UserUpdateDto updateUserDto) {
        User oldUser = userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException(String.format("user with id=%d not found", id)));

        if (!updateUserDto.getEmail().equals(oldUser.getEmail())) {
            oldUser.setEmail(updateUserDto.getEmail());
        }

        if (!updateUserDto.getFirstName().equals(oldUser.getFirstName())) {
            oldUser.setFirstName(updateUserDto.getFirstName());
        }

        if (!updateUserDto.getLastName().equals(oldUser.getLastName())) {
            oldUser.setLastName(updateUserDto.getLastName());
        }

        return userRepository.save(oldUser);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    //TODO: edit, delete
    public void deleteUserByUsername(String username) {
        List<String> auth = getAuthUser();
        Optional<User> user = findByUsername(username);

        if (user.isEmpty()) {
            return;
        } else {
            if (auth.contains("ROLE_ADMIN")) {
                Optional<RefreshToken> refreshToken = tokenRepository.findByUser(user.get());
                refreshToken.ifPresent(tokenRepository::delete);
                userRepository.delete(user.get());
            } else {
                throw new NotFoundException(String.format("У вас нет доступа: %s", auth));
            }
        }
    }

    public void deleteUserById(String id) {
        List<String> auth = getAuthUser();

        if (auth.contains("ROLE_ADMIN")) {
            User user = userRepository.findUserById(Long.parseLong(id)).orElseThrow(() -> new NotFoundException("not found user with id=" + id));

            userRepository.delete(user);
        }
    }

    public List<String> getAuthUser() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }



    public User editUserByUsername(String username) {
        User oldUser = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("username=%s not found", username)));


        return null;
    }

    public User editUserByEmail(String email) {
        User oldUser = findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("user with email=%s not found", email)));

        return null;
    }
}