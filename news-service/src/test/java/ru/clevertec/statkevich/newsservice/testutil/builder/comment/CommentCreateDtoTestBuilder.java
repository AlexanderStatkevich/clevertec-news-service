package ru.clevertec.statkevich.newsservice.testutil.builder.comment;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentCreateDto;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

@NoArgsConstructor
@AllArgsConstructor
@With
public class CommentCreateDtoTestBuilder implements Builder<CommentCreateDto> {

    private String text;

    private Long newsId;

    public static CommentCreateDtoTestBuilder createCommentCreateDto() {
        return new CommentCreateDtoTestBuilder();
    }

    @Override
    public CommentCreateDto build() {
        return new CommentCreateDto(text, newsId);
    }

}
