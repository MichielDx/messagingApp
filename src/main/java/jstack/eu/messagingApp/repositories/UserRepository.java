package jstack.eu.messagingApp.repositories;

import jstack.eu.messagingApp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
