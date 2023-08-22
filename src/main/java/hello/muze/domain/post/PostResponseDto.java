package hello.muze.domain.post;

import hello.muze.domain.comment.CommentResponseDto;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import java.util.List;
@Data
@Builder
public class PostResponseDto {

    private Long id;

    private Integer memberId;

    private List<String> attachments;

    private List<CommentResponseDto> comment;

    private List<Long> heartsId;
    private String memberNickName;


    @NotEmpty(message = "제목을 입력해주세요")
    private String title;
    @Lob
    @NotEmpty(message = "내용을 입력해주세요")
    private String contents;

    @ColumnDefault("0")
    private Integer view;//조회수

    @ColumnDefault("0")
    private Integer heartCount;//조회수

    @NotEmpty(message = "카테고리를 선택해주세요")
    private String categoryType;

}
