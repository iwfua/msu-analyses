package ru.banksystem.training.kostyaback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.banksystem.training.kostyaback.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);

    Optional<User> findUserById(Long id);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);

    void deleteUserById(Long id);
}