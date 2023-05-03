package hello.muze.domain.mesage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hello.muze.domain.member.Member;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Data
public class ChatMessage {
    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, ENTER2
    }
    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String contents; // 메시지
    private String sender;

}
