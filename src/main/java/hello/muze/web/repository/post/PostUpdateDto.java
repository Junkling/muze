package hello.muze.web.repository.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostUpdateDto {
    private String title;
    private String contents;
    private LocalDateTime updated;

    private Integer categoryId;

    public PostUpdateDto(String title, String contents, Integer categoryId) {

        this.title = title;
        this.contents = contents;
        this.categoryId = categoryId;
        updated = LocalDateTime.now();
    }
}
