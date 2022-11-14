package hello.muze.web.repository.member.jpa;

import hello.muze.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Integer> {
//    boolean existsByMemberId(String loginId);
//    boolean existsByNickname(String nickname);
//    boolean existsByEmail(String email);
//
//    @Query("select m from member m where m.loginId like :loginId")
//    Optional<Member> findMember(@Param("loginId") String loginId);
}
