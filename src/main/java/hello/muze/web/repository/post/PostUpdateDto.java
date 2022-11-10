package hello.muze.web.repository.post;

import hello.muze.domain.category.CategoryType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class PostUpdateDto {
    private String title;
    private String contents;
    private LocalDateTime updated;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryName;

    public PostUpdateDto(String title, String contents, CategoryType categoryName) {

        this.title = title;
        this.contents = contents;
        this.categoryName = categoryName;
        updated = LocalDateTime.now();
    }
}
