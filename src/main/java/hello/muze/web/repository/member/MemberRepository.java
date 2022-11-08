package hello.muze.web.repository.member;

import hello.muze.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
public interface MemberRepository {
    Member save(Member member);

    void update(Integer memberId, MemberUpdateDto updateDto);

    Optional<Member> findById(Integer id);

    Optional<Member> findMember(String LoginId);

    void delete(Integer userId);
}