package pl.pwr.news.service.category;

import pl.pwr.news.model.category.Category;

import java.util.List;

/**
 * Created by jakub on 3/9/16.
 */
public interface CategoryService {

    Category createCategory(Category category);

    void addTag(Long categoryId, Long... tagId);

    void updateCategory(Category category);

    List<Category> findAll();

    List<Category> findAll(Iterable<Long> categoryIds);

    Category findById(Long id);

    Category findByName(String name);

    boolean exist(Long categoryId);

    Long countAll();

}
