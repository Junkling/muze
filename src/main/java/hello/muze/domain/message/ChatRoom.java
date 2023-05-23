package hello.muze.domain.message;

import hello.muze.domain.member.Member;
import hello.muze.web.service.message.ChatService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Auditable;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    private String roomId;

    private String name;

    private Set<WebSocketSession> sessions = new HashSet<>();

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender()+"님이 입장하셨습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    private <T> void sendMessage(T chatMessage, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, chatMessage));
    }

}



