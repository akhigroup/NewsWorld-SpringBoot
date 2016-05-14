package pl.pwr.news.repository.stereotype;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.stereotype.Stereotype;

/**
 * Created by falfasin on 5/12/16.
 */
@Repository
public interface StereotypeRepository extends CrudRepository<Stereotype, Long> {

    Stereotype findByName(String name);
}
