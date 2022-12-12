package hello.muze.web.service.heart;

import hello.muze.domain.heart.Heart;
import hello.muze.domain.member.Member;

import java.io.IOException;

public interface HeartServiceInterface {
    void save(Heart heart, Long postId, Member member) throws IOException;

    void delete(Long postId, Member member) throws IOException;

    Integer heartCheck(Long postId, Member member);
}
