package pl.pwr.news.repository.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.category.Category;

/**
 * Created by jakub on 2/29/16.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(String name);
}
