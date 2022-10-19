package hello.muze.domain.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostUpdateDto {
    private String title;
    private String contents;
    private LocalDateTime updated;

    public PostUpdateDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
        updated = LocalDateTime.now();
    }
}
