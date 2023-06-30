package ru.clevertec.statkevich.userservice.mailing;


import ru.clevertec.statkevich.userservice.domain.User;

public record UserRegisteredEvent(
        String email,
        String fullName
) {
    public UserRegisteredEvent(User user) {
        this(user.getEmail(), user.getFullName());
    }
}
