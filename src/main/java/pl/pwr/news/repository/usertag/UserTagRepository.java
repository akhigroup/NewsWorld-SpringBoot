package pl.pwr.news.repository.usertag;

import org.springframework.data.repository.CrudRepository;
import pl.pwr.news.model.usertag.UserTag;

/**
 * Created by falfasin on 5/11/16.
 */
public interface UserTagRepository extends CrudRepository<UserTag, Long> {

    UserTag findOneByUser_IdAndTag_Id(Long userId, Long tagId);
}


/*
example of usage:
User user = new User();
user.setRole(UserRole.USER);
Tag tag = new Tag("test");
UserTag userTag = new UserTag();
userRepository.save(user);
tagRepository.save(tag);
userTag.setUser(user);
userTag.setTag(tag);
user.addUserTag(userTag);
tag.addUserTag(tag);
userTagRepository.save(userTag);
 */