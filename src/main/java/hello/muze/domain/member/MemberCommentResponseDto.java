package hello.muze.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberCommentResponseDto {
    private Long commentId;
    private String contents;
    private Long postId;
    private String postTitle;
}
