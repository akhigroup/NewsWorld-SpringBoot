package pl.pwr.news.repository.usertag;

import org.springframework.data.repository.CrudRepository;
import pl.pwr.news.model.usertag.UserTag;

/**
 * Created by falfasin on 5/11/16.
 */
public interface UserTagRepository extends CrudRepository<UserTag, Long> {

    UserTag findOneByUser_TokenAndTag_Id(String token, Long tagId);
}