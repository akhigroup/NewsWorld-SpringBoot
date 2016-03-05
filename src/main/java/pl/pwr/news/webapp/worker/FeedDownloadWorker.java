package pl.pwr.news.webapp.worker;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.source.FeedSource;
import pl.pwr.news.provider.ArticlesProvider;
import pl.pwr.news.provider.RssFeed;
import pl.pwr.news.provider.UpdatableArticlesSourceComposite;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.feedsource.FeedSourceService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Evelan-E6540 on 05.03.2016.
 */
@Component
@Log4j
public class FeedDownloadWorker {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    ArticleService articleService;
    @Autowired
    FeedSourceService feedSourceService;
    ArticlesProvider articlesProvider;

    /**
     * Starts with app
     */
    @Scheduled(initialDelay = 10000, fixedRate = 3600 * 1000)
    public void reportCurrentTime() {
        log.info("Parser start" + dateFormat.format(new Date()));
        feedSourceService.add("TVN24", "http://www.tvn24.pl/najwazniejsze.xml");
        feedSourceService.add("BBC", "http://feeds.bbci.co.uk/news/rss.xml");
        feedSourceService.add("Facet WP.pl", "http://facet.wp.pl/rss.xml?smgnzebaxbaxhefticaid=116990");

        gatherArticles();
    }

    /**
     * Gather all articles from enabled feed sources in database
     */
    public void gatherArticles() {
        UpdatableArticlesSourceComposite allFeeds = new UpdatableArticlesSourceComposite();
        List<FeedSource> feedSources = feedSourceService.findAll();
        for (FeedSource fs : feedSources) {
            String sourceLink = fs.getLink();
            log.info("Source link: " + sourceLink);
            allFeeds.addSource(new RssFeed(sourceLink));
            //TODO - wywalić rss feed i zmienić na feedSource ;)
        }

        articlesProvider = new ArticlesProvider(allFeeds, new ArticlesProvider.UpdateListener() {
            @Override
            public void onUpdated(List<Article> articles) {
                for (Article article : articles) {
                    articleService.save(article);
                }
            }

            @Override
            public void onUpdateFailed() {

            }
        });
        articlesProvider.update();
    }
}
