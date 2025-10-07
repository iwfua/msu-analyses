package ru.banksystem.training.kostyaback.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "text_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, length = 70)
    private String key;

    @Column(nullable = false, length = 255)
    private String value;
}
