package pl.pwr.news.service.user;

import java.util.Set;

/**
 * Created by Jasiek on 22.04.2016.
 */
public interface UserTagsService {
    void updateTagsValues(Long userId, Set<Long> tagIds, Long delta);

    double getTagsFactor(Long userId, Set<Long> tagIds);

    void normalizeValues(Long userId);


    Long getSumOfTagValues(Long userId, Set<Long> tagIds);

    Long getSumOfAllTags(Long userId);

}
