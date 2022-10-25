//package hello.muze.repository.post.jpa;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import hello.muze.domain.post.Post;
//import hello.muze.repository.post.PostRepository;
//import hello.muze.repository.post.PostSearchCond;
//import hello.muze.repository.post.PostUpdateDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//
//@Repository
//@RequiredArgsConstructor
//@Transactional
//public class JpaPostRepository implements PostRepository {
//
//    private final SpringDataJpaPostRepository repository;
//
//    @Override
//    public Post save(Post post) {
//        return repository.save(post);
//    }
//
//    @Override
//    public void update(Long postId, PostUpdateDto updateDto) {
//        Post findPost = repository.findById(postId).orElseThrow();
//        findPost.setTitle(updateDto.getTitle());
//        findPost.setContents(updateDto.getContents());
//        findPost.setCategoryId(updateDto.getCategoryId());
//        findPost.setUpdated(LocalDateTime.now());
//    }
//
//    @Override
//    public Optional<Post> findById(Long id) {
//        return repository.findById(id);
//    }
//
//
//    @Override
//    public List<Post> findByCategory(PostSearchCond cond) {
//        Integer categoryId = cond.getCategoryId();
//        return repository.findByCategoryLike(categoryId);
//    }
//
//    @Override
//    public List<Post> findAll(PostSearchCond cond) {
//        String title = cond.getTitle();
//        Integer categoryId = cond.getCategoryId();
//        String contents = cond.getContents();
//        return repository.findAll();
//    }
//
//}
