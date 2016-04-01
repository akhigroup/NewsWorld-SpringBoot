package pl.pwr.news.service.tag;

import org.springframework.stereotype.Service;
import pl.pwr.news.model.tag.Tag;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public interface TagService {

    Tag createTag(Tag tag);

    void addCategory(Long tagId, Long... categoryId);

    List<Tag> findAll();

    List<Tag> findAll(Iterable<Long> tagIds);

    Tag findById(Long id);

    Tag findByName(String name);

    boolean exist(Long tagId);

    Long countAll();

}
