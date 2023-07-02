package ru.clevertec.statkevich.newsservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentCreateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;

/**
 * Mapper for generation entity from dto and vice versa via MapStruct.
 */

@Mapper(uses = {UsernameMapper.class, BaseEntityMapper.class})
public interface CommentMapper {

    CommentVo toVo(Comment source);

    @Mapping(target = "news", source = "newsId")
    @Mapping(source = "source", target = "username", qualifiedByName = "getUsername")
    Comment toEntity(CommentCreateDto source);

    Comment toEntity(CommentUpdateDto source);

    void map(CommentUpdateDto source, @MappingTarget Comment target);

}
