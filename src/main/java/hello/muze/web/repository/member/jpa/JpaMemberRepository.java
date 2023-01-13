package hello.muze.web.repository.member.jpa;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.member.MemberUpdateDto;
import hello.muze.web.service.login.PwChangeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
    private final SpringDataJpaMemberRepository repository;


    @Override

    public Member save(Member member) {
        repository.save(member);
        return member;

    }

    @Override
    public void update(Integer memberId, MemberUpdateDto memberUpdateDto) {
        Member findId = repository.findById(memberId).orElseThrow();
        findId.setNickName(memberUpdateDto.getNickName());
        findId.setProfile(memberUpdateDto.getProfile());
    }


    @Override
    public Optional<Member> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Member> findByMember(String loginId) {
        return repository.findAll().stream().filter(member->member.getLoginId().equals(loginId)).findFirst();
    }
    @Override
    public Optional<Member> findByNickName(String nickName) {
        return repository.findAll().stream().filter(member->member.getNickName().equals(nickName)).findFirst();
    }
    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findAll().stream().filter(member->member.getEmail().equals(email)).findFirst();
    }

    @Override
    public Optional<Member> findByMemberAndEmail(String loginId, String email) {
        return repository.findAll().stream().filter(member->member.getEmail().equals(email)&&member.getLoginId().equals(loginId)).findFirst();
    }

    @Override
    public void delete(Integer userId) {
        repository.deleteById(userId);
    }


    /**
     * save시 email 확인 프로세스 추가
     */

}

