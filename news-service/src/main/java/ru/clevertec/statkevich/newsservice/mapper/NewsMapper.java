package ru.clevertec.statkevich.newsservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import ru.clevertec.statkevich.newsservice.domain.News;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;
import ru.clevertec.statkevich.newsservice.dto.news.NewsCreateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsSingleVo;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.news.NewsUpdateVo;
import ru.clevertec.statkevich.newsservice.dto.news.NewsVo;

/**
 * Mapper for generation entity from dto and vice versa via MapStruct.
 */


@Mapper(uses = UsernameMapper.class)
public interface NewsMapper {

    NewsVo toVo(News source);

    NewsUpdateVo toUpdateVo(News source);

    NewsSingleVo toSingleVo(News source);

    NewsVo toVo(News news, Page<CommentVo> commentVos);

    @Mapping(source = "source", target = "username", qualifiedByName = "getUsername")
    News toEntity(NewsCreateDto source);

    void map(NewsUpdateDto source, @MappingTarget News target);
}
