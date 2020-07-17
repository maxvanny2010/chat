package chat.repository;

import chat.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * PersonRepository.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/8/2020
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
