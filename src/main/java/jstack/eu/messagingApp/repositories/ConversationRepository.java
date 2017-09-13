package jstack.eu.messagingApp.repositories;

import jstack.eu.messagingApp.models.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepository extends MongoRepository<Conversation, String> {

}
