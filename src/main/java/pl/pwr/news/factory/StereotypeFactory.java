package pl.pwr.news.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.pwr.news.converter.ValueConverter;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.service.stereotype.StereotypeService;

/**
 * Created by falfasin on 5/12/16.
 */
@Component
public class StereotypeFactory {

    @Autowired
    StereotypeService stereotypeService;

    public Stereotype getInstance(String name) {
        Stereotype existingStereotype = stereotypeService.findByName(name);
        if (existingStereotype != null) {
            return existingStereotype;
        }
        return new Stereotype(ValueConverter.convertTagName(name));
    }
}
