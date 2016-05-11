package pl.pwr.news.service.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.model.user.User;
import pl.pwr.news.model.usertag.UserTag;
import pl.pwr.news.repository.category.CategoryRepository;
import pl.pwr.news.repository.tag.TagRepository;
import pl.pwr.news.repository.user.UserRepository;
import pl.pwr.news.repository.usertag.UserTagRepository;
import pl.pwr.news.service.exception.TagNotExist;
import pl.pwr.news.service.exception.UserNotExist;
import pl.pwr.news.service.exception.UserTagNotExist;

import java.util.List;
import java.util.Optional;

/**
 * Created by jakub on 3/9/16.
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTagRepository userTagRepository;

    @Override
    public Tag createTag(Tag tag) {
        Optional<Tag> tagWithNotUniqueName = Optional.ofNullable(tagRepository.findByName(tag.getName()));
        if (tagWithNotUniqueName.isPresent()) {
            return tagWithNotUniqueName.get();
        }
        return tagRepository.save(tag);
    }

    @Override
    public Long incrementTagValue(Long userId, Long tagId) throws UserNotExist, TagNotExist, UserTagNotExist {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findOne(userId));
        if (!userOptional.isPresent())
            throw new UserNotExist("User not exist for: " + userId);
        Optional<Tag> tagOptional = Optional.ofNullable(tagRepository.findOne(tagId));
        if (!tagOptional.isPresent())
            throw new TagNotExist("Tag not exist for: " + tagId);
        Optional<UserTag> userTagOptional = Optional.ofNullable(
                userTagRepository.findOneByUser_IdAndTag_Id(userId, tagId));
        if (!userTagOptional.isPresent())
            throw new UserTagNotExist(
                    "UserTag not exist for userId: " + userId + ", tagId: " + tagId);
        UserTag existingUserTag = userTagOptional.get();
        existingUserTag.incrementTagValue();
        userTagRepository.save(existingUserTag);
        return existingUserTag.getTagValue();
    }

    @Override
    public List<Tag> findAll() {
        return (List<Tag>) tagRepository.findAll();
    }

    @Override
    public List<Tag> findAll(Iterable<Long> tagIds) {
        return (List<Tag>) tagRepository.findAll(tagIds);
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public boolean exist(Long tagId) {
        return tagRepository.exists(tagId);
    }

    @Override
    public Long countAll() {
        return tagRepository.count();
    }
}
