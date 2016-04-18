package pl.pwr.news.service.tag;

import org.springframework.stereotype.Service;
import pl.pwr.news.model.tag.Tag;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public interface TagService {

    Tag createOrGetExisting(Tag tag) throws NotUniqueTag;

    List<Tag> findAll();

    List<Tag> findAll(Iterable<Long> tagIds);

    Tag findById(Long id) throws TagNotExist;

    Tag findByName(String name) throws TagNotExist;

    boolean exist(Long tagId) throws TagNotExist;

    Long countAll();

}
