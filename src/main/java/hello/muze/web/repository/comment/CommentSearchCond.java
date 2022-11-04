package hello.muze.web.repository.comment;

import lombok.Data;

@Data
public class CommentSearchCond {
    private Long id;
    private Long postId;

    public CommentSearchCond(Long id, Long postId) {
        this.id = id;
        this.postId = postId;
    }
    public CommentSearchCond() {
    }
}
