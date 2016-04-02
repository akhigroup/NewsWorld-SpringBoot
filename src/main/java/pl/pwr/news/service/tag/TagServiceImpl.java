package pl.pwr.news.service.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.category.CategoryRepository;
import pl.pwr.news.repository.tag.TagRepository;

import java.util.List;
import java.util.Optional;

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
        Optional<Tag> tagWithNotUniqueName = Optional.ofNullable(tagRepository.findByName(tag.getName()));
        if (tagWithNotUniqueName.isPresent()) {
            return tagWithNotUniqueName.get();
        }
        return tagRepository.save(tag);
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
