package pl.pwr.news.service.tag;

import org.springframework.stereotype.Service;
import pl.pwr.news.model.tag.Tag;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public interface TagService {

    Tag createTag(String name);

    List<Tag> findAll();

    Tag findById(Long id);

    boolean exist(Long tagId);

    Long countAll();

}
