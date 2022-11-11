package hello.muze.web.repository.member;

import hello.muze.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
public interface MemberRepository {
    Member save(Member member);

    void update(Integer memberId, MemberUpdateDto updateDto);

    Optional<Member> findById(Integer id);

    Optional<Member> findByMember(String loginId);

    Optional<Member> findByNickName(String nickName);
    Optional<Member> findByEmail(String email);

    void delete(Integer userId);
}