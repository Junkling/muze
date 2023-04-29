package hello.muze.domain.mesage;

import hello.muze.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Auditable;

import javax.persistence.*;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private Member receiver;

    public void setSender(Member sender) {
        this.sender = sender;
    }

}


