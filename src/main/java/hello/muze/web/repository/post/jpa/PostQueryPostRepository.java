package hello.muze.web.repository.post.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.post.PostSearchCond;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static hello.muze.domain.post.QPost.post;


@Repository
@Transactional
public class PostQueryPostRepository {


    private final EntityManager em;
    private final JPAQueryFactory query;
    public PostQueryPostRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }
    public List<Post> findPost(PostSearchCond cond) {
        String title = cond.getTitle();
        String categoryType = cond.getCategoryType();
        String contents = cond.getContents();
        List<Post> posts = query
                .select(post)
                .from(post)
                .where(categorySame(categoryType),titleLike(title), contentLike(contents))
                .fetch();
        return posts;
    }

    public List<Post> hotPost() {
        List<Post> hotPosts = query
                .select(post)
                .from(post)
                .orderBy(post.heartCount.desc())
                .offset(0)
                .limit(5)
                .fetch();
        return hotPosts;
    }


    private BooleanExpression categorySame(String categoryType) {
        if (StringUtils.hasText(categoryType)) {
            return post.categoryType.like(categoryType);
        }
        return null;
    }
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
