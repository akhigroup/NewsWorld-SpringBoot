package pl.pwr.news.service.article;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.repository.article.ArticleRepository;

import java.util.Date;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.elasticsearch.common.lang3.StringUtils.isNotBlank;
import static org.springframework.data.jpa.domain.Specifications.where;
import static pl.pwr.news.repository.article.ArticleSpecification.keywordInText;
import static pl.pwr.news.repository.article.ArticleSpecification.keywordInTitle;

/**
 * Created by jakub on 2/29/16.
 */
@Service
@Log4j
public class ArticleSerivceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public void save(Article entity) {

        String title = entity.getTitle();
        String link = entity.getLink();
        if (isBlank(title) || isBlank(link)) {
            log.error("Title or link are empty!");
            return;
        }

        entity.setAddedDate(new Date().getTime());
        entity.setVisible(true);
        articleRepository.save(entity);
        log.info("Article saved");
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
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public void update(Article article) {
        boolean articleNotExist = !articleRepository.exists(article.getId());

        if (articleNotExist) {
            log.error("Article not exist!");
            return;
        }

        String title = article.getTitle();
        if (isNotBlank(title)) {
            article.setTitle(title);
        }

        String text = article.getText();
        if (isNotBlank(text)) {
            article.setText(text);
        }

        String imageUrl = article.getImageUrl();
        if (isNotBlank(imageUrl)) {
            article.setImageUrl(imageUrl);
        }

        String link = article.getLink();
        if (isNotBlank(link)) {
            article.setLink(link);
        }
        articleRepository.save(article);
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
