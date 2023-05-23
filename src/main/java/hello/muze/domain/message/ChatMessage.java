package hello.muze.domain.message;

import hello.muze.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Column(nullable = false)
    private String message;

    private MessageType type;

    private String roomId;

    @Column(nullable = false)
    private LocalDateTime sendTime;

    private String sender;

    private Long userCount;


}
