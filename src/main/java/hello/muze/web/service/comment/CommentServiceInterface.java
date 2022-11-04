package hello.muze.web.service.comment;

import hello.muze.domain.comment.Comment;
import hello.muze.web.repository.comment.CommentSearchCond;
import hello.muze.web.repository.comment.CommentUpdateDto;
import hello.muze.web.repository.post.PostSearchCond;

import java.util.List;
import java.util.Optional;

public interface CommentServiceInterface {
    Comment save(Comment comment);

    void update(Long commentId, CommentUpdateDto updateParam);

    Optional<Comment> findById(Long id);

//    List<Post> findByCategory(PostSearchCond postSearchCond);

    List<Comment> findPost(CommentSearchCond cond);
}
