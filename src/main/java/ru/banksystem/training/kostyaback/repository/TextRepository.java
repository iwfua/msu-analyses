package ru.banksystem.training.kostyaback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.banksystem.training.kostyaback.domain.Text;

import java.util.Optional;

@Repository
public interface TextRepository extends JpaRepository<Text, Long> {

    Optional<Text> findByIdentif(String identif);

}
