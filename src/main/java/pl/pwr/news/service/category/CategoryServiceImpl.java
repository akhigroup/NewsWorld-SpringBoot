package pl.pwr.news.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.category.CategoryRepository;
import pl.pwr.news.repository.tag.TagRepository;
import pl.pwr.news.service.tag.TagNotExist;

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
    public void addTag(Long categoryId, Long... tagIds) throws CategoryNotExist, TagNotExist {
        if (!categoryRepository.exists(categoryId)) {
            throw new CategoryNotExist(categoryId);
        }

        Category category = categoryRepository.findOne(categoryId);

        for (Long tagId : tagIds) {
            if (!tagRepository.exists(tagId)) {
                throw new TagNotExist(tagId);
            }
            Tag tag = tagRepository.findOne(tagId);
            category.addTag(tag);
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) throws CategoryNotExist {
        Long categoryId = category.getId();

        if (!categoryRepository.exists(categoryId)) {
            throw new CategoryNotExist(categoryId);
        }
        //  ???
        //TODO - a czo to :D
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
    public Category findById(Long id) throws CategoryNotExist {
        if (!categoryRepository.exists(id)) {
            throw new CategoryNotExist(id);
        }

        return categoryRepository.findOne(id);
    }

    @Override
    public Category findByName(String name) throws CategoryNotExist {
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findByName(name));

        if (!categoryOptional.isPresent()) {
            throw new CategoryNotExist("Category not exist by name: " + name);
        }

        return categoryOptional.get();
    }

    @Override
    public boolean exist(Long categoryId) throws CategoryNotExist {
        if (!categoryRepository.exists(categoryId)) {
            throw new CategoryNotExist(categoryId);
        }

        return true;
    }

    @Override
    public Long countAll() {
        return categoryRepository.count();
    }
}
