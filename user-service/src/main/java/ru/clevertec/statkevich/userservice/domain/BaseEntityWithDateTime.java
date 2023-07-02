package ru.clevertec.statkevich.userservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntityWithDateTime {

    @Id
    @Column(name = "id", updatable = false)
    private UUID uuid;
    @Column(name = "date_time_create")
    private LocalDateTime dateTimeCreate;
    @Version
    @Column(name = "date_time_update")
    private LocalDateTime dateTimeUpdate;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @PrePersist
    void initDateCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.dateTimeCreate = now;
        this.dateTimeUpdate = now;
    }

    @PreUpdate
    void updateDateTimeUpdate() {
        this.dateTimeUpdate = LocalDateTime.now();
    }
}
