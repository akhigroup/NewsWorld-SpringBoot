package pl.pwr.news.service.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.repository.article.ArticleRepository;

import java.util.List;

/**
 * Created by jakub on 2/29/16.
 */
@Service
public class ArticleSerivceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public void save(Article entity) {
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
}
