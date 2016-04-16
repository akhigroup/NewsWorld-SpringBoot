package pl.pwr.news.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.user.User;

/**
 * Created by Evelan-E6540 on 27.02.2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByActivationHash(String activationHash);

    User findByToken(String token);
}
