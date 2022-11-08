package hello.muze.web.repository.comment.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.muze.domain.comment.Comment;
import hello.muze.domain.comment.QComment;
import hello.muze.web.repository.comment.CommentRepository;
import hello.muze.web.repository.comment.CommentSearchCond;
import hello.muze.web.repository.comment.CommentUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static hello.muze.domain.comment.QComment.comment;
import static hello.muze.domain.post.QPost.post;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public JpaCommentRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public void update(Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment findComment = em.find(Comment.class, commentId);
        findComment.setContents(commentUpdateDto.getContents());

    }

    @Override
    public List<Comment> findByPostId(CommentSearchCond cond) {
        Long postId = cond.getPostId();
        List<Comment> comments = query
                .select(comment)
                .from(comment)
                .where(postIdLike(postId))
                .fetch();
        return null;
    }

    @Override
    public Optional<Comment> findById(Long postId) {
        Comment findComment = em.find(Comment.class, postId);
        return Optional.ofNullable(findComment);
    }

    private BooleanExpression postIdLike(Long postId) {
        if (postId!=null) {
            return comment.post.id.like(Long.toString(postId));
        }
        return null;
    }

}
