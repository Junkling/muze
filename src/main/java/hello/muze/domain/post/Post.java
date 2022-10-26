package hello.muze.domain.post;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Data
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer memberId;

    private String memberName;
    @NotEmpty(message = "제목을 입력해주세요")
    private String title;
    @NotEmpty(message = "내용을 입력해주세요")
    private String contents;

    private LocalDateTime created;

    private LocalDateTime updated;
    private Integer categoryId;
    private String categoryName;

    public Post() {
    }

    public Post(Integer memberId, String memberName, String title, String contents, Integer categoryId) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.title = title;
        this.contents = contents;
        this.categoryId = categoryId;
    }
}
