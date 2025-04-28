package com.green.greenshare.note.controller;

import com.green.greenshare.note.dto.MessageDTO;
import com.green.greenshare.note.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageStompController {

  private final SimpMessagingTemplate messagingTemplate;
  private final MessageService messageService; // ✅ DB 저장용 서비스 주입

  /**
   * WebSocket으로 /app/note/send 경로로 쪽지를 받으면 처리
   */
  @MessageMapping("/note/send")
  public void sendNote(MessageDTO messageDTO) {
    log.info("💬 WebSocket 쪽지 수신: {}", messageDTO);

    // 1. DB에 쪽지 저장
    messageService.insertNote(messageDTO);

    // 2. 수신자에게 실시간으로 쪽지 전송
    messagingTemplate.convertAndSendToUser(
            messageDTO.getReceiverEmail(), // 수신자 이메일
            "/queue/notes",                 // 수신자가 구독하고 있는 Queue
            messageDTO                      // 전송할 메시지 데이터
    );
  }




}
