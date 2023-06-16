package ru.clevertec.statkevich.newsservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.NewsCreateDto;
import ru.clevertec.statkevich.newsservice.dto.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.NewsVo;

@Mapper
public interface NewsMapper {

    NewsVo toDto(News news);

    News toEntity(NewsCreateDto newsCreateDto);

    News toEntity(NewsUpdateDto newsUpdateDto);

    void map(NewsUpdateDto source, @MappingTarget News target);
}
