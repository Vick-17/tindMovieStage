package com.tindMovie.tindMovie.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

public class MessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long message_id;

  private String content;
  private Long sender_id;
  private Long recipient_id;
  private Date timestamp;
  private Long conversation_id;


    public MessageEntity(String messageText, String id, Long recipientId) {
}

    public Long getMessage_id() {
        return this.message_id;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSender_id() {
        return this.sender_id;
    }

    public void setSender_id(Long sender_id) {
        this.sender_id = sender_id;
    }

    public Long getRecipient_id() {
        return this.recipient_id;
    }

    public void setRecipient_id(Long recipient_id) {
        this.recipient_id = recipient_id;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getConversation_id() {
        return this.conversation_id;
    }

    public void setConversation_id(Long conversation_id) {
        this.conversation_id = conversation_id;
    }

}
