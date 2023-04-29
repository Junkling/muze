package hello.muze.web.repository.message;

import hello.muze.domain.member.Member;
import hello.muze.domain.mesage.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySender(Member member);
}
