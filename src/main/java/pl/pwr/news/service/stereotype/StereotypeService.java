package pl.pwr.news.service.stereotype;

import pl.pwr.news.model.stereotype.Stereotype;

import java.util.List;

/**
 * Created by jakub on 3/9/16.
 */
public interface StereotypeService {

    Stereotype createStereotype(Stereotype stereotype);

    void addTag(Long stereotypeId, Long... tagId);

    void updateStereotype(Stereotype stereotype);

    List<Stereotype> findAll();

    List<Stereotype> findAll(Iterable<Long> stereotypeIds);

    Stereotype findById(Long id);

    Stereotype findByName(String name);

    boolean exist(Long stereotypeId);

    Long countAll();

}
