package hello.muze.web.repository.member.jpa;

import hello.muze.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Integer> {
}
