package pl.pwr.news.service.tag;

import com.google.common.base.CharMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.tag.TagRepository;

import java.util.List;

import static org.apache.commons.lang.StringUtils.deleteWhitespace;
import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Created by jakub on 3/9/16.
 */
@Service
public class TagServiceImpl implements TagService {


    private static final CharMatcher ALNUM = CharMatcher.inRange('a', 'z')
            .or(CharMatcher.ASCII)
            .or(CharMatcher.inRange('A', 'Z'))
            .or(CharMatcher.inRange('0', '9'))
            .precomputed();

    @Autowired
    TagRepository tagRepository;

    /**
     * Create new tag
     *
     * @param name - tag name, should be unique
     * @return - returns create tag, if name of tag is not unique returns existing tag
     */
    @Override
    public Tag createTag(String name) {

        if (isBlank(name)) {
            return null;
        }

        name = name.trim();
        name = name.toLowerCase();
        name = ALNUM.retainFrom(name);
        name = deleteWhitespace(name);
        //TODO - przydałyby się testy do tego

        Tag tag = tagRepository.findByName(name);

        if (tag != null) {
            return tag;
        }

        tag = new Tag(name);
        tagRepository.save(tag);
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return (List<Tag>) tagRepository.findAll();
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findOne(id);
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
