package pl.pwr.news.service.stereotype;

import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.service.exception.StereotypeNotExist;
import pl.pwr.news.service.exception.UserNotExist;
import pl.pwr.news.service.exception.UserStereotypeNotExist;

import java.util.List;

/**
 * Created by jakub on 3/9/16.
 */
public interface StereotypeService {

    Stereotype createStereotype(Stereotype stereotype);

    void addTag(Long stereotypeId, Long... tagId);

    void updateStereotype(Stereotype stereotype);

    Long incrementValue(Long userId, Long stereotypeId) throws UserNotExist, StereotypeNotExist, UserStereotypeNotExist;

    List<Stereotype> findAll();

    List<Stereotype> findAll(Iterable<Long> stereotypeIds);

    Stereotype findById(Long id);

    Stereotype findByName(String name);

    boolean exist(Long stereotypeId);

    Long countAll();

}
