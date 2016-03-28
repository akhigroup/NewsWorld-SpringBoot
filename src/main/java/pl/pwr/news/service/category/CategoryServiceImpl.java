package pl.pwr.news.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.repository.category.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * Created by jakub on 3/9/16.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Creates new category in database, if category is not unique returns existing entity in database with given name
     * If you want update category use UpdateCategory
     *
     * @param name     - unique name
     * @param imageUrl - category image
     * @return created category, or already existing if name not unique
     */
    @Override
    public Category createCategory(String name, String imageUrl) {

        if (isBlank(name)) {
            return null;
        }
        name = name.trim();

        Optional<Category> categoryWithNotUniqueName = Optional.ofNullable(categoryRepository.findByName(name));
        if (categoryWithNotUniqueName.isPresent()) {
            return categoryWithNotUniqueName.get();
        }


        Category category = new Category(name);

        if (isNotBlank(imageUrl)) {
            imageUrl = imageUrl.trim();
            category.setImageUrl(imageUrl);
        }

        categoryRepository.save(category);
        return category;
    }

    @Override
    public void updateCategory(Category category) {
        Long categoryId = category.getId();

        if (!categoryRepository.exists(categoryId)) {
            return;
        }
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public boolean exist(Long categoryId) {
        return categoryRepository.exists(categoryId);
    }

    @Override
    public Long countAll() {
        return categoryRepository.count();
    }
}
