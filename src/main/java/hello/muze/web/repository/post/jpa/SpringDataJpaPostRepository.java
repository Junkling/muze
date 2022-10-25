//package hello.muze.repository.post.jpa;
//
//import hello.muze.domain.post.Post;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.*;
//
//public interface SpringDataJpaPostRepository extends JpaRepository<Post, Long> {
//    List<Post> findByTitleLike(String title);
//
//    List<Post> findByCategoryLike(Integer categoryId);
//
//    List<Post> findByMemberNameLike(String memberName);
//
//    List<Post> findByTitleLikeAndMemberNameLike(String title, String memberName);
//
//    @Query("select p from Post p where p.title like :title and p.memberName like memberName")
//    List<Post> findPosts(@Param("title") String title, @Param("memberName") String memberName);
//}
