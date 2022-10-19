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
    private String title;
    private String contents;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Integer categoryId;

}
