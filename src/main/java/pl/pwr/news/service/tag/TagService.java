package pl.pwr.news.service.tag;

import org.springframework.stereotype.Service;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.exception.TagNotExist;
import pl.pwr.news.service.exception.UserNotExist;
import pl.pwr.news.service.exception.UserTagNotExist;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public interface TagService {

    Tag createTag(Tag tag);

    Long incrementTagValue(Long userId, Long stereotypeId) throws UserNotExist, TagNotExist, UserTagNotExist;

    List<Tag> findAll();

    List<Tag> findAll(Iterable<Long> tagIds);

    Tag findById(Long id);

    Tag findByName(String name);

    boolean exist(Long tagId);

    Long countAll();

}
