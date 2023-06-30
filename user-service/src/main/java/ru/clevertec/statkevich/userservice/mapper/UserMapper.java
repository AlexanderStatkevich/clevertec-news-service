package ru.clevertec.statkevich.userservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.statkevich.userservice.domain.User;
import ru.clevertec.statkevich.userservice.dto.UserCreateUpdateDto;
import ru.clevertec.statkevich.userservice.dto.UserDto;
import ru.clevertec.statkevich.userservice.dto.UserRegistrationDto;

@Mapper
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(UserCreateUpdateDto source);

    @Mapping(target = "password", ignore = true)
    User toEntity(UserRegistrationDto source);

    UserDto toDto(User source);

    void map(UserCreateUpdateDto source, @MappingTarget User target);
}
