package pl.pwr.news.service.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.provider.ArticlesProvider;
import pl.pwr.news.provider.RssFeed;
import pl.pwr.news.provider.UpdatableArticlesSourceComposite;
import pl.pwr.news.repository.article.ArticleRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.jpa.domain.Specifications.where;
import static pl.pwr.news.repository.article.ArticleSpecification.keywordInText;
import static pl.pwr.news.repository.article.ArticleSpecification.keywordInTitle;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public class ArticleSerivceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    ArticlesProvider articlesProvider;
    // Nie jestem pewien, czy to może być tutaj, czy powinno być jako osobny serwis/komponent.
    public ArticleSerivceImpl() {
        UpdatableArticlesSourceComposite allFeeds = new UpdatableArticlesSourceComposite();
        allFeeds.addSource(new RssFeed("http://www.tvn24.pl/najwazniejsze.xml"));
        allFeeds.addSource(new RssFeed("http://feeds.bbci.co.uk/news/rss.xml"));
        allFeeds.addSource(new RssFeed("http://facet.wp.pl/rss.xml?smgnzebaxbaxhefticaid=116990"));
        articlesProvider = new ArticlesProvider(allFeeds, new ArticlesProvider.UpdateListener() {
            @Override
            public void onUpdated(List<Article> articles) {
                for (Article article : articles) {
                    save(article);
                }
            }

            @Override
            public void onUpdateFailed() {

            }
        });
        articlesProvider.update();
        articlesProvider.setAutoUpdate(TimeUnit.MINUTES.toMillis(5));
    }

    @Override
    public void save(Article entity) {
        entity.setAddedDate(new Date().getTime());
        articleRepository.save(entity);
    }

    @Override
    public void delete(Article entity) {
        entity.setVisible(false);
        articleRepository.save(entity);
    }

    @Override
    public List<Article> findAll() {
        return (List<Article>) articleRepository.findAll();
    }

    @Override
    public Article findById(Long id) {
        return articleRepository.findOne(id);
    }

    @Override
    public List<Article> findAll(String keyword, String link) {
        return articleRepository.findAll(where(keywordInTitle(keyword)).or(where(keywordInText(keyword))));
    }
}
