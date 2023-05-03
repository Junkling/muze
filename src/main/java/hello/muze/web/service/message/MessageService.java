package hello.muze.web.service.message;

import hello.muze.domain.member.Member;
import hello.muze.domain.mesage.ChatMessage;
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

    public ChatMessage sendMessage(MessageDto messageDto) {
        Member sender = memberRepository.findById(messageDto.getSenderId()).orElseThrow();
        Member receiver = memberRepository.findById(messageDto.getReceiverId()).orElseThrow();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContents(messageDto.getContents());
        chatMessage.setSender(sender.getNickName());

        ChatMessage saved = messageRepository.save(chatMessage);
        return saved;
    }
    @Transactional(readOnly = true)
    public List<ChatMessage> sentMessage(Member member) {
        return messageRepository.findAllBySender(member);
    }

}
