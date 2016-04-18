package pl.pwr.news.service.category;

import pl.pwr.news.model.category.Category;
import pl.pwr.news.service.tag.TagNotExist;

import java.util.List;

/**
 * Created by jakub on 3/9/16.
 */
public interface CategoryService {

    Category createCategory(Category category);

    void addTag(Long categoryId, Long... tagId) throws CategoryNotExist, TagNotExist;

    void updateCategory(Category category) throws CategoryNotExist;

    List<Category> findAll();

    List<Category> findAll(Iterable<Long> categoryIds);

    Category findById(Long id) throws CategoryNotExist;

    Category findByName(String name) throws CategoryNotExist;

    boolean exist(Long categoryId) throws CategoryNotExist;

    Long countAll();

}
