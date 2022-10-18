package hello.muze.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MemberRepositoryTest {
    MemberRepository memberRepository = new MemberRepository();
    @AfterEach

    void afterEach() {
        MemberRepository memberRepository = new MemberRepository();
    }

    @Test
    void save() {
        Member member = new Member("jun", "wnsgur", "123132", "123@naver.com");

        Member saveId = memberRepository.save(member);

        Member findById = memberRepository.findById(member.getId());
        Assertions.assertThat(findById).isEqualTo(saveId);
    }
}