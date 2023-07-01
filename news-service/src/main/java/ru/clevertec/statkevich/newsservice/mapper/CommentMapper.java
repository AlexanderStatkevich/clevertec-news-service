package ru.clevertec.statkevich.newsservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentCreateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentVo;

/**
 * Mapper for generation entity from dto and vice versa via MapStruct.
 */
@Mapper
public interface CommentMapper {

    CommentVo toVo(Comment comment);

    Comment toEntity(CommentCreateDto commentCreateDto);

    Comment toEntity(CommentUpdateDto commentUpdateDto);

    void map(CommentUpdateDto source, @MappingTarget Comment target);

}
