package pl.pwr.news.service.userstereotype;

import org.springframework.stereotype.Service;
import pl.pwr.news.model.userstereotype.UserStereotype;

/**
 * Created by jf on 5/6/16.
 */

@Service
public interface UserStereotypeService {

    UserStereotype findOne(Long userId, Long stereotypeId);
}
