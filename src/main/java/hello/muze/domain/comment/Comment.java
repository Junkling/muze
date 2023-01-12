package hello.muze.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hello.muze.domain.member.Member;
import hello.muze.domain.post.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "댓글 내용을 입력해주세요")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "postId")
    @JsonIgnoreProperties({"post"})
    private Post post;

    @ManyToOne
    @JoinColumn(name = "memberId")
    @JsonIgnoreProperties({"member"})
    private Member member;

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;


}
