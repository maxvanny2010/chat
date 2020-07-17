package chat.repository;

import chat.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * RoomRepository.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/17/2020
 */
@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
}
