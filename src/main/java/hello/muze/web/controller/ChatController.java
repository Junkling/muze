package hello.muze.web.controller;

import hello.muze.domain.message.ChatMessage;
import hello.muze.domain.message.ChatRoom;
import hello.muze.web.repository.message.ChatRoomRepository;
import hello.muze.web.service.message.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestBody String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAll() {
        return chatService.findAllRoom();
    }
}
