package hello.muze.web.repository.post;

//import hello.muze.domain.post.category.CategoryType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class PostUpdateDto {
    private String title;
    private String contents;
    private LocalDateTime updated;

//    @Enumerated(EnumType.STRING)
    private String categoryType;

    public PostUpdateDto(String title, String contents, String categoryType) {

        this.title = title;
        this.contents = contents;
        this.categoryType = categoryType;
        updated = LocalDateTime.now();
    }
}
