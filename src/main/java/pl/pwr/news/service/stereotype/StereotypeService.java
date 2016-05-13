package pl.pwr.news.service.stereotype;

import org.springframework.stereotype.Service;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.service.exception.NotUniqueStereotype;
import pl.pwr.news.service.exception.StereotypeNotExist;

import java.util.List;

/**
 * Created by falfasin on 5/12/16.
 */
@Service
public interface StereotypeService {

    List<Stereotype> findAll();

    boolean exist(Long stereotypeId);

    Stereotype findById(Long stereotypeId);

    Stereotype create(Stereotype stereotype) throws NotUniqueStereotype;

    Stereotype update(Stereotype stereotype) throws StereotypeNotExist;

    Stereotype findByName(String name);

    void addTag(Long stereotypeId, Long... tagIds);
}
