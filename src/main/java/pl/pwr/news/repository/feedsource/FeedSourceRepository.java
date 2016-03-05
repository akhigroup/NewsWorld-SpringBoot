package pl.pwr.news.repository.feedsource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.news.model.source.FeedSource;

/**
 * Created by jakub on 2/29/16.
 */
@Repository
public interface FeedSourceRepository extends CrudRepository<FeedSource, Long> {

    FeedSource findByLink(String link);
}
