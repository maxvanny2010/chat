package chat.repository;

import chat.model.Message;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * MessageRepository.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/17/2020
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Transactional
    @Modifying
    @Query("delete FROM Message m WHERE m.room.id=?1 and m.id=?2")
    void deleteByRoomIdAndId(int id, int msgID);
}
