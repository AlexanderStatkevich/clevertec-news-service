package ru.clevertec.statkevich.newsservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.statkevich.newsservice.domain.Comment;
import ru.clevertec.statkevich.newsservice.dto.CommentCreateDto;
import ru.clevertec.statkevich.newsservice.dto.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.dto.CommentVo;

@Mapper
public interface CommentMapper {

    CommentVo toDto(Comment comment);

    Comment toEntity(CommentCreateDto commentCreateDto);

    Comment toEntity(CommentUpdateDto commentUpdateDto);

    void map(CommentUpdateDto source, @MappingTarget Comment target);

}
