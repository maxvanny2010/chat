package chat.repository;

import chat.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * MessageRepository.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/17/2020
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
}
