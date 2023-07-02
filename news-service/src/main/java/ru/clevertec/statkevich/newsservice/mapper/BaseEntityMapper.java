package ru.clevertec.statkevich.newsservice.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mapstruct.Mapper;
import org.mapstruct.TargetType;
import ru.clevertec.statkevich.newsservice.domain.BaseEntity;

@Mapper
public abstract class BaseEntityMapper {

    @PersistenceContext
    private EntityManager entityManager;

    public <T extends BaseEntity> T toEntity(Long id, @TargetType Class<T> clazz) {
        return entityManager.find(clazz, id);
    }
}
