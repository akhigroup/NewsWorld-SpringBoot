package pl.pwr.news.repository.tag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.tag.Tag;

/**
 * Created by jakub on 2/29/16.
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByName(String name);
}
