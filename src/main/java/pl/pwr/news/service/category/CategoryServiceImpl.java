package pl.pwr.news.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.model.user.User;
import pl.pwr.news.repository.category.CategoryRepository;
import pl.pwr.news.repository.tag.TagRepository;
import pl.pwr.news.repository.user.UserRepository;
import pl.pwr.news.service.exception.CategoryNotExist;
import pl.pwr.news.service.exception.UserNotExist;

import java.util.List;
import java.util.Optional;

/**
 * Created by jakub on 3/9/16.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public Category createCategory(Category category) {
        Optional<Category> categoryWithNotUniqueName = Optional.ofNullable(categoryRepository.findByName(category.getName()));
        if (categoryWithNotUniqueName.isPresent()) {
            return categoryWithNotUniqueName.get();
        }
        return categoryRepository.save(category);
    }

    @Override
    public void addTag(Long categoryId, Long... tagIds) {
        if (!categoryRepository.exists(categoryId)) {
            return;
        }

        Category category = categoryRepository.findOne(categoryId);

        for (Long tagId : tagIds) {
            if (!tagRepository.exists(tagId)) {
                continue;
            }
            Tag tag = tagRepository.findOne(tagId);
            category.addTag(tag);
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
        Long categoryId = category.getId();

        if (!categoryRepository.exists(categoryId)) {
            return;
        }
        //  ???
    }

    @Override
    public List<Category> findAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public List<Category> findAll(Iterable<Long> categoryIds) {
        return (List<Category>) categoryRepository.findAll(categoryIds);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
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
