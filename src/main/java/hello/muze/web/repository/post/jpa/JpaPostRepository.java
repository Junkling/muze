package hello.muze.web.repository.post.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.muze.domain.post.Post;
import hello.muze.domain.post.QPost;
import hello.muze.web.repository.post.PostRepository;
import hello.muze.web.repository.post.PostSearchCond;
import hello.muze.web.repository.post.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static hello.muze.domain.post.QPost.post;


@Repository
@Transactional
public class JpaPostRepository implements PostRepository {


    private final EntityManager em;
    private final JPAQueryFactory query;
    public JpaPostRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public void update(Long postId, PostUpdateDto updateDto) {
        Post findPost = em.find(Post.class,postId);
        findPost.setTitle(updateDto.getTitle());
        findPost.setContents(updateDto.getContents());
        findPost.setCategoryName(updateDto.getCategoryName());
    }


    @Override
    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findPost(PostSearchCond cond) {
        String title = cond.getTitle();
//        Integer categoryId = cond.getCategoryId();
        String contents = cond.getContents();
//        BooleanBuilder categoryDef = new BooleanBuilder(post.categoryId.eq(categoryId));
        List<Post> posts = query
                .select(post)
                .from(post)
                .where(titleLike(title),contentLike(contents))
                .fetch();
        return posts;
    }

//    @Override
//    public List<Post> findByCategory(PostSearchCond cond) {
////        Integer categoryId = cond.getCategoryId();
//
//        BooleanBuilder categoryDef = new BooleanBuilder(post.categoryId.eq(categoryId));
//
//        List<Post> posts = query
//                .select(post)
//                .from(post)
//                .where(categoryDef)
//                .fetch();
//        return posts;
//    }

    private BooleanExpression titleLike(String title) {
        if (StringUtils.hasText(title)) {
            return post.title.like("%" + title + "%");
        }
        return null;
    }
    private BooleanExpression contentLike(String contents) {
        if (StringUtils.hasText(contents)) {
            return post.title.like("%" + contents + "%");
        }
        return null;
    }

}
