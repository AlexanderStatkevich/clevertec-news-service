package ru.clevertec.statkevich.newsservice.domain;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Abstract class for common fields.
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Unique identifier of entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Creation time of entity
     */
    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "text")
    private String text;

    @Column(name = "username")
    private String username;

    @PrePersist
    void initDateCreate() {
        this.time = LocalDateTime.now();
    }
}
