package com.tindMovie.tindMovie.WebSocket;

import com.tindMovie.tindMovie.Model.MessageEntity;
import com.tindMovie.tindMovie.Repository.MessageRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

  // Créez un dictionnaire pour stocker les sessions WebSocket des clients.
  private Map<String, WebSocketSession> sessions = new HashMap<>();

  // Injectez un service ou un repository de messages si nécessaire.
  private MessageRepository messageRepository;

  public MyWebSocketHandler(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session)
    throws Exception {
    // Nouvelle connexion WebSocket établie. Ajoutez la session à la liste.
    sessions.put(session.getId(), session);
  }

  @Override
  protected void handleTextMessage(
    WebSocketSession session,
    TextMessage message
  ) throws Exception {
    // Récupérez le contenu du message texte.
    String messageText = message.getPayload();

    // Obtenez l'ID du destinataire du message (vous devez implémenter cette logique).
    Long recipientId = getRecipientIdFromMessageContent(messageText);

    // Insérez le message en base de données.
    MessageEntity newMessage = new MessageEntity(
      messageText,
      session.getId(),
      recipientId
    );
    messageRepository.save(newMessage);

    // Diffusez le message à tous les clients connectés, y compris l'expéditeur.
    for (WebSocketSession clientSession : sessions.values()) {
      try {
        clientSession.sendMessage(message);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void afterConnectionClosed(
    WebSocketSession session,
    CloseStatus status
  ) throws Exception {
    // La connexion WebSocket a été fermée. Retirez la session de la liste.
    sessions.remove(session.getId());
  }

  private Long getRecipientIdFromMessageContent(String messageContent) {
    // Implémentez la logique pour extraire l'ID du destinataire à partir du contenu du message.
    // Cela peut dépendre de votre application et de la façon dont vous structurez vos messages.
    // Vous pouvez extraire l'ID du destinataire à partir du contenu du message, par exemple.
    // Assurez-vous de gérer les erreurs et les cas où l'ID du destinataire n'est pas trouvé.
    // Retournez l'ID du destinataire.
  }
}
