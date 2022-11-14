package hello.muze.domain.post;

//import hello.muze.domain.post.category.CategoryType;
import hello.muze.domain.comment.Comment;
import hello.muze.domain.member.Member;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId")
    private Member member;

//    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
//    @JoinColumn(name = "commentId")
//    private List<Comment> comment;

    @ColumnDefault("0")
    private Integer likeCount;


    @NotEmpty(message = "제목을 입력해주세요")
    private String title;
    @Lob
    @NotEmpty(message = "내용을 입력해주세요")
    private String contents;


    @ColumnDefault("0")
    private Integer view;//조회수

    @CreationTimestamp
    private Timestamp created;

    @UpdateTimestamp
    private Timestamp updated;

//    @ColumnDefault("FREE")
//    @Enumerated(EnumType.STRING)
    private String categoryType;
    
}
