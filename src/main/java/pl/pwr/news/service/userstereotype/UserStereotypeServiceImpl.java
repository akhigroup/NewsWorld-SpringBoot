package pl.pwr.news.service.userstereotype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.userstereotype.UserStereotype;
import pl.pwr.news.repository.userstereotype.UserStereotypeRepository;

/**
 * Created by jf on 5/6/16.
 */

@Service
public class UserStereotypeServiceImpl implements UserStereotypeService {

    @Autowired
    UserStereotypeRepository userStereotypeRepository;

    @Override
    public UserStereotype findOne(Long userId, Long stereotypeId) {
        return userStereotypeRepository.findOneByUser_IdAndStereotype_Id(userId, stereotypeId);
    }
}
