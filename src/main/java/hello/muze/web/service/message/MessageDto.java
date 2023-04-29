package hello.muze.web.service.message;

import lombok.Data;

@Data
public class MessageDto {
    private String contents;
//
//    private String senderName;
//
//    private String receiverName;

    private Integer receiverId;

    private Integer senderId;

}
