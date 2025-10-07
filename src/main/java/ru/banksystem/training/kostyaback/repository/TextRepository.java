package ru.banksystem.training.kostyaback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.banksystem.training.kostyaback.domain.Text;
import ru.banksystem.training.kostyaback.domain.User;

@Repository
public interface TextRepository extends JpaRepository<Text, Long> {

    Text findById(String id);

    Text findByKey(String key);
}
