package pl.pwr.news.service.stereotype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.model.user.User;
import pl.pwr.news.model.userstereotype.UserStereotype;
import pl.pwr.news.repository.stereotype.StereotypeRepository;
import pl.pwr.news.repository.tag.TagRepository;
import pl.pwr.news.repository.user.UserRepository;
import pl.pwr.news.repository.userstereotype.UserStereotypeRepository;
import pl.pwr.news.service.exception.StereotypeNotExist;
import pl.pwr.news.service.exception.UserNotExist;
import pl.pwr.news.service.exception.UserStereotypeNotExist;

import java.util.List;
import java.util.Optional;

/**
 * Created by jakub on 3/9/16.
 */
@Service
public class StereotypeServiceImpl implements StereotypeService {

    @Autowired
    StereotypeRepository stereotypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserStereotypeRepository userStereotypeRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public Stereotype createStereotype(Stereotype stereotype) {
        Optional<Stereotype> stereotypeWithNotUniqueName = Optional.ofNullable(stereotypeRepository.findByName(stereotype.getName()));
        if (stereotypeWithNotUniqueName.isPresent()) {
            return stereotypeWithNotUniqueName.get();
        }
        return stereotypeRepository.save(stereotype);
    }

    @Override
    public void addTag(Long stereotypeId, Long... tagIds) {
        if (!stereotypeRepository.exists(stereotypeId)) {
            return;
        }

        Stereotype stereotype = stereotypeRepository.findOne(stereotypeId);

        for (Long tagId : tagIds) {
            if (!tagRepository.exists(tagId)) {
                continue;
            }
            Tag tag = tagRepository.findOne(tagId);
            stereotype.addTag(tag);
        }
        stereotypeRepository.save(stereotype);
    }

    @Override
    public void updateStereotype(Stereotype stereotype) {
        Long stereotypeId = stereotype.getId();

        if (!stereotypeRepository.exists(stereotypeId)) {
            return;
        }
        //  ???
    }

    @Override
    public Long incrementValue(Long userId, Long stereotypeId) throws UserNotExist, StereotypeNotExist, UserStereotypeNotExist {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findOne(userId));
        if (!userOptional.isPresent())
            throw new UserNotExist("User not exist for: " + userId);
        Optional<Stereotype> stereotypeOptional = Optional.ofNullable(stereotypeRepository.findOne(stereotypeId));
        if (!stereotypeOptional.isPresent())
            throw new UserNotExist("Stereotype not exist for: " + stereotypeId);
        Optional<UserStereotype> userStereotypeOptional = Optional.ofNullable(
                userStereotypeRepository.findOneByUser_IdAndStereotype_Id(userId, stereotypeId));
        if (!userStereotypeOptional.isPresent())
            throw new UserStereotypeNotExist(
                    "UserStereotype not exist for userId: " + userId + ", stereotypeId: " + stereotypeId);
        UserStereotype existingUserStereotype = userStereotypeOptional.get();
        existingUserStereotype.incrementValue();
        userStereotypeRepository.save(existingUserStereotype);
        return existingUserStereotype.getValue();
    }

    @Override
    public List<Stereotype> findAll() {
        return (List<Stereotype>) stereotypeRepository.findAll();
    }

    @Override
    public List<Stereotype> findAll(Iterable<Long> stereotypeIds) {
        return (List<Stereotype>) stereotypeRepository.findAll(stereotypeIds);
    }

    @Override
    public Stereotype findById(Long id) {
        return stereotypeRepository.findOne(id);
    }

    @Override
    public Stereotype findByName(String name) {
        return stereotypeRepository.findByName(name);
    }

    @Override
    public boolean exist(Long stereotypeId) {
        return stereotypeRepository.exists(stereotypeId);
    }

    @Override
    public Long countAll() {
        return stereotypeRepository.count();
    }
}
