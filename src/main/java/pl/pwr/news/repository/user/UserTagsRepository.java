package pl.pwr.news.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.pwr.news.model.user.UserTagsValues;

import java.util.List;

/**
 * Created by Jasiek on 18.04.2016.
 */
public interface UserTagsRepository extends MongoRepository<UserTagsValues, Long> {
    UserTagsValues findById(Long id);

    @Query("{_id: ?0},{tagsValues: {$elemMatch: {tag_id: ?1}}}")
    UserTagsValues getTagValue(Long userId, Long tagId);

    List<UserTagsValues> findAll();

    Long getsumOfValuesById(Long userId);

}
