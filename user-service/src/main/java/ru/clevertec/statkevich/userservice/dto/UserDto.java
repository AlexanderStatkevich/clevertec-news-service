package ru.clevertec.statkevich.userservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.clevertec.statkevich.userservice.domain.UserRole;
import ru.clevertec.statkevich.userservice.domain.UserStatus;
import ru.clevertec.statkevich.userservice.serialization.UnixSerializer;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({
        "uuid",
        "dt_create",
        "dt_update",
        "mail",
        "fio",
        "role",
        "status"
})
public record UserDto(
        UUID uuid,
        @JsonProperty(value = "dt_create")
        @JsonSerialize(using = UnixSerializer.class)
        LocalDateTime dateTimeCreate,
        @JsonProperty(value = "dt_update")
        @JsonSerialize(using = UnixSerializer.class)
        LocalDateTime dateTimeUpdate,
        @JsonProperty(value = "mail")
        String email,
        @JsonProperty(value = "fio")
        String fullName,
        UserRole role,
        UserStatus status
) {
}
