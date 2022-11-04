package hello.muze.web.repository.comment;

import lombok.Data;

@Data
public class CommentUpdateDto {
    private String contents;

    public CommentUpdateDto(String contents) {
        this.contents = contents;
    }
    public CommentUpdateDto() {
    }


}
