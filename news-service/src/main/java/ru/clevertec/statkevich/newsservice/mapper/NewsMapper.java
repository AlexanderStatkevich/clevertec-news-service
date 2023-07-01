package ru.clevertec.statkevich.newsservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.dto.news.NewsCreateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsVo;

/**
 * Mapper for generation entity from dto and vice versa via MapStruct.
 */
@Mapper
public interface NewsMapper {

    NewsVo toVo(News news);

    NewsVo toVo(News news, Page<CommentVo> commentVos);

    News toEntity(NewsCreateDto newsCreateDto);

    News toEntity(NewsUpdateDto newsUpdateDto);

    void map(NewsUpdateDto source, @MappingTarget News target);
}
