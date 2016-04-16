package pl.pwr.news.service.feedsource;

import org.springframework.stereotype.Service;
import pl.pwr.news.model.source.FeedSource;

import java.util.List;

/**
 * Created by Evelan-E6540 on 05.03.2016.
 */
@Service
public interface FeedSourceService {

    void add(String title, String link);

    void update(FeedSource feedSource);

    void setSourceChecked(FeedSource feedSource);

    List<FeedSource> findAll();

    FeedSource findByLink(String link);

    FeedSource findById(Long id);

}
