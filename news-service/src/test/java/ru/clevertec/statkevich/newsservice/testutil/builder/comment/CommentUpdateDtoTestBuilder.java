package ru.clevertec.statkevich.newsservice.testutil.builder.comment;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.statkevich.newsservice.dto.comment.CommentUpdateDto;
import ru.clevertec.statkevich.newsservice.testutil.builder.api.Builder;

@NoArgsConstructor
@AllArgsConstructor
@With
public class CommentUpdateDtoTestBuilder implements Builder<CommentUpdateDto> {

    private String text = "text";


    public static CommentUpdateDtoTestBuilder createCommentUpdateDto() {
        return new CommentUpdateDtoTestBuilder();
    }

    @Override
    public CommentUpdateDto build() {
        return new CommentUpdateDto(text);
    }
}
