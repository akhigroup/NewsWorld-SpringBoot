package pl.pwr.news.worker;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.provider.wykop.WykopAPI;
import pl.pwr.news.provider.wykop.WykopAPIImpl;
import pl.pwr.news.provider.wykop.WykopArticleDTO;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.article.NotUniqueArticle;

import java.util.List;

/**
 * Created by Evelan on 17/04/16.
 */
@Component
@Log4j
public class WykopWorker {

    @Autowired
    ArticleService articleService;

    @Autowired
    WykopAPI wykopAPI;

//    @Scheduled(initialDelay = 10000, fixedRate = 60000)
    public void wykopParser() {

        log.info("Wykop parser ----------------------------------- start");
        List<WykopArticleDTO> wykopArticles = wykopAPI.getWykopApiArticles(WykopAPIImpl.EXAMPLE_URL);

        for (WykopArticleDTO wykopArticle : wykopArticles) {

            Article article = new Article();
            article.setTitle(wykopArticle.getTitle());

            String link = wykopArticle.getSourceUrl();
            if (StringUtils.isBlank(link)) {
                link = wykopArticle.getUrl();
            }

            article.setLink(link);
            article.setText(wykopArticle.getDescription());
            article.setImageUrl(wykopArticle.getPreview());

            try {
                articleService.create(article);
            } catch (NotUniqueArticle notUniqueArticle) {
                log.warn(notUniqueArticle.getMessage());
            }

        }

        log.info("Wykop parser ----------------------------------- stop");
    }

}
