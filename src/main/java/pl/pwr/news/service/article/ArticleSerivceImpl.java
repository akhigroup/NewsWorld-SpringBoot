package pl.pwr.news.service.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.repository.article.ArticleRepository;

import java.util.Date;
import java.util.List;

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
