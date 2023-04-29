package hello.muze.web.service.message;

import hello.muze.domain.member.Member;
import hello.muze.domain.mesage.Message;
import hello.muze.web.repository.member.MemberRepository;
import hello.muze.web.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    public Message sendMessage(MessageDto messageDto) {
        Member sender = memberRepository.findById(messageDto.getSenderId()).orElseThrow();
        Member receiver = memberRepository.findById(messageDto.getReceiverId()).orElseThrow();

        Message message = new Message();
        message.setContents(messageDto.getContents());
        message.setSender(sender);

        Message saved = messageRepository.save(message);
        return saved;
    }
    @Transactional(readOnly = true)
    public List<Message> sentMessage(Member member) {
        return messageRepository.findAllBySender(member);
    }

}
