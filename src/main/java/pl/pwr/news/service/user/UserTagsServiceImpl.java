package pl.pwr.news.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.user.UserTagsValues;
import pl.pwr.news.repository.user.UserTagsRepository;

import java.util.Set;

/**
 * Created by Jasiek on 22.04.2016.
 */
@Service
public class UserTagsServiceImpl implements UserTagsService {

    @Autowired
    UserTagsRepository userTagsRepository;

    @Override
    public void updateTagsValues(Long userId, Set<Long> tagIds, Long delta) {
        UserTagsValues userTagsValues = userTagsRepository.findById(userId);

        Long newSumOfValues = userTagsValues.getSumOfValues();

        for (Long tagId : tagIds) {
            if (userTagsValues.getTags().containsKey(tagId)) {
                userTagsValues.getTags().replace(tagId, userTagsValues.getTags().get(tagId) + delta);
            } else {
                userTagsValues.getTags().put(tagId, delta);
            }
            newSumOfValues += delta;
        }

        userTagsValues.setSumOfValues(newSumOfValues);

        userTagsRepository.save(userTagsValues);
    }

    @Override
    public double getTagsFactor(Long userId, Set<Long> tagIds) {
        UserTagsValues userTagsValues = userTagsRepository.findById(userId);

        Long sumOfAll = userTagsValues.getSumOfValues();
        if (sumOfAll == 0) return 0;

        Long sum = 0L;
        for (Long tagId : tagIds) {
            if (userTagsValues.getTags().containsKey(tagId)) sum += userTagsValues.getTags().get(tagId);
        }

        double average = sum.doubleValue() / (double) tagIds.size();

        return average / sumOfAll.doubleValue();
    }

    @Override
    public void normalizeValues(Long userId) {
        UserTagsValues userTagsValues = userTagsRepository.findById(userId);

        Long newSum = 0L;
        for (Long tagId : userTagsValues.getTags().keySet()) {
            Long newValue = userTagsValues.getTags().get(tagId);

            newValue = newValue / 2;
            //TODO zapodać jakąś sprytną funkcję (matematyczną)

            if (newValue == 0L) userTagsValues.getTags().remove(tagId);
            else userTagsValues.getTags().replace(tagId, newValue);

            newSum += newValue;
        }

        userTagsValues.setSumOfValues(newSum);

        userTagsRepository.save(userTagsValues);
    }

    @Override
    public Long getSumOfTagValues(Long userId, Set<Long> tagIds) {
        UserTagsValues userTagsValues = userTagsRepository.findById(userId);

        Long sum = 0L;
        for (Long tagId : tagIds) {
            sum += userTagsValues.getTags().get(tagId);
        }

        return sum;
    }

    @Override
    public Long getSumOfAllTags(Long userId) {
        return userTagsRepository.getsumOfValuesById(userId);
    }
}
