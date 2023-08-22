package hello.muze.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    @NotEmpty(message = "댓글 내용을 입력해주세요")
    private String contents;

    private Long postId;

    private Integer memberId;

}
