package hello.muze.domain.mesage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hello.muze.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String contents;

    @ManyToOne
    @JoinColumn(name = "senderId")
    @JsonIgnoreProperties({"member"})
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private ChatRoom chatRoom;

    @CreationTimestamp
    private Timestamp sendTime;

    @ColumnDefault("false")
    private Boolean confirm;

}
