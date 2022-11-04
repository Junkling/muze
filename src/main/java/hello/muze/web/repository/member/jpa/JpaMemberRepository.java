package hello.muze.web.repository.member.jpa;

import hello.muze.domain.member.Member;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.member.MemberUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        member.setCreated(LocalDateTime.now());
        repository.save(member);
        return member;

    }

    @Override
    public void update(Integer memberId, MemberUpdateDto updateDto) {
        Member findId = repository.findById(memberId).orElseThrow();
        findId.setNickName(updateDto.getNickName());
        findId.setPassword(updateDto.getPassword());
        findId.setProfile(updateDto.getProfile());
        findId.setUpdated(LocalDateTime.now());
    }

    @Override
    public Optional<Member> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Member> findMember(String loginId) {
        return repository.findAll().stream().filter(member->member.getLoginId().equals(loginId)).findFirst();
    }


    /**
     * save시 email 확인 프로세스 추가
     */


}

