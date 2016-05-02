package pl.pwr.news.repository.stereotype;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.stereotype.Stereotype;

/**
 * Created by jakub on 2/29/16.
 */
@Repository
public interface StereotypeRepository extends CrudRepository<Stereotype, Long> {

    Stereotype findByName(String name);
}
