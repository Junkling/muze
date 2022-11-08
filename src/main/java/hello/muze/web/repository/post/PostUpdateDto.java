package hello.muze.web.repository.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostUpdateDto {
    private String title;
    private String contents;
    private LocalDateTime updated;

    private String categoryName;

    public PostUpdateDto(String title, String contents, String categoryName) {

        this.title = title;
        this.contents = contents;
        this.categoryName = categoryName;
        updated = LocalDateTime.now();
    }
}
