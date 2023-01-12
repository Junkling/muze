package hello.muze.domain.post;

import hello.muze.domain.attachment.Attachment;
import hello.muze.domain.comment.Comment;
import hello.muze.domain.heart.Heart;
import hello.muze.domain.member.Member;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.List;

@Data
public class PostAddForm {

    private Member member;

    private List<MultipartFile> attachments;


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
