package chat.repository;

import chat.model.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * AuthorityRepository.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/17/2020
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
}
