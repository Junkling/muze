package hello.muze.web.repository.message;

import hello.muze.domain.member.Member;
import hello.muze.domain.message.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllBySender(Member member);
}
