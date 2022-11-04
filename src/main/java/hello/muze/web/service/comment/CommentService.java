package hello.muze.web.service.comment;

import hello.muze.domain.comment.Comment;
import hello.muze.web.repository.comment.CommentRepository;
import hello.muze.web.repository.comment.CommentSearchCond;
import hello.muze.web.repository.comment.CommentUpdateDto;
import hello.muze.web.repository.post.PostSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceInterface {

    private final CommentRepository repository;

    @Override
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public void update(Long commentId, CommentUpdateDto updateParam) {
        repository.update(commentId, updateParam);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Comment> findPost(CommentSearchCond cond) {
        return repository.findByPostId(cond);
    }

}
