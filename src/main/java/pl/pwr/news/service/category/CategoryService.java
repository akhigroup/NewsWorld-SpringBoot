package pl.pwr.news.service.category;

import pl.pwr.news.model.category.Category;

import java.util.List;

/**
 * Created by jakub on 3/9/16.
 */
public interface CategoryService {

    Category createCategory(String name, String imageUrl);

    void updateCategory(Category category);

    List<Category> findAll();

    Category findById(Long id);

    boolean exist(Long categoryId);
}
