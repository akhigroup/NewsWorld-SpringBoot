package pl.pwr.news.service.feedsource;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.source.FeedSource;
import pl.pwr.news.repository.feedsource.FeedSourceRepository;

import java.util.Date;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Created by Evelan-E6540 on 05.03.2016.
 */
@Service
@Log4j
public class FeedSourceServiceImpl implements FeedSourceService {

    @Autowired
    FeedSourceRepository sourceRepository;

    /**
     * Add new data source to database
     *
     * @param title - title of source
     * @param link  - unique link
     */
    @Override
    public void add(String title, String link) {
        if (isBlank(link) || isBlank(title)) {
            log.error("Blank link or title");
            return;
        }

        boolean linkIsUnique = findByLink(link) == null;

        if (linkIsUnique) {
            FeedSource feedSource = new FeedSource(title, link);
            feedSource.setAddedDate(new Date().getTime());
            sourceRepository.save(feedSource);
            log.info("FeedSource saved: " + title + " - " + link);
        }
        log.error("FeedSource not saved " + title + " - " + link);
    }

    /**
     * Update existing source feed in database
     *
     * @param feedSource - feed source from database
     */
    @Override
    public void update(FeedSource feedSource) {
        //TODO - jak to fajnie oprogramować? to niżej jest trochę chujowe
        boolean feedSourceExist = sourceRepository.exists(feedSource.getId());
        if (feedSourceExist) {
            sourceRepository.save(feedSource);
        }
    }

    /**
     * Updated last check date
     *
     * @param feedSource - checked feed source, from data base
     */
    @Override
    public void setSourceChecked(FeedSource feedSource) {
        boolean feedSourceExist = feedSource.getId() != null;
        if (feedSourceExist) {
            feedSource.setLastChecked(new Date().getTime());
            sourceRepository.save(feedSource);
        }
    }

    @Override
    public List<FeedSource> findAll() {
        return (List<FeedSource>) sourceRepository.findAll();
    }

    @Override
    public FeedSource findById(Long id) {
        return sourceRepository.findOne(id);
    }

    @Override
    public FeedSource findByLink(String link) {
        return sourceRepository.findByLink(link);
    }
}
