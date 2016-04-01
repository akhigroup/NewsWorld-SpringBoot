package pl.pwr.news.service.tag;

import com.google.common.base.CharMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.category.CategoryRepository;
import pl.pwr.news.repository.tag.TagRepository;

import java.util.List;

/**
 * Created by jakub on 3/9/16.
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void addCategory(Long tagId, Long... categoryIds) {
        if (!tagRepository.exists(tagId)) {
            return;
        }
        Tag tag = tagRepository.findOne(tagId);

        for (Long categoryId : categoryIds) {
            if (!categoryRepository.exists(categoryId)) {
                continue;
            }
            Category category = categoryRepository.findOne(categoryId);
            tag.addCategory(category);
        }
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> findAll() {
        return (List<Tag>) tagRepository.findAll();
    }

    @Override
    public List<Tag> findAll(Iterable<Long> tagIds) {
        return (List<Tag>) tagRepository.findAll(tagIds);
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public boolean exist(Long tagId) {
        return tagRepository.exists(tagId);
    }

    @Override
    public Long countAll() {
        return tagRepository.count();
    }
}
