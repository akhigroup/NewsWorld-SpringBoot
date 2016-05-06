package pl.pwr.news.repository.userstereotype;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.userstereotype.UserStereotype;

/**
 * Created by jf on 5/6/16.
 */

@Repository
public interface UserStereotypeRepository extends CrudRepository<UserStereotype, Long> {

    UserStereotype findOneByUser_IdAndStereotype_Id(Long userId, Long stereotypeId);
}


//example of usage:
//        User user = new User();
//        user.setRole(UserRole.USER);
//        Stereotype stereotype = new Stereotype();
//        UserStereotype userStereotype = new UserStereotype();
//        userRepository.save(user);
//        stereotypeRepository.save(stereotype);
//        userStereotype.setUser(user);
//        userStereotype.setStereotype(stereotype);
//        user.addUserStereotype(userStereotype);
//        stereotype.addUserStereotype(userStereotype);
//        userStereotypeRepository.save(userStereotype);


// do wywalenia
