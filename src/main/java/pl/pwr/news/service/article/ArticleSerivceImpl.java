package pl.pwr.news.service.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.article.ArticleRepository;
import pl.pwr.news.repository.category.CategoryRepository;
import pl.pwr.news.repository.tag.TagRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public Article createOrUpdate(Article entity) {
        Optional<Long> articleId = Optional.ofNullable(entity.getId());
        boolean articleNotExist = !articleRepository.exists(articleId.orElse(-1L));

        if (articleNotExist) {
            entity.setAddedDate(new Date().getTime());
        }

        articleRepository.save(entity);
        return entity;
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
    public Article findById(Long id) {
        return articleRepository.findOne(id);
    }

    @Override
    public List<Article> findAll(String keyword, String link) {
        return articleRepository.findAll(where(keywordInTitle(keyword)).or(where(keywordInText(keyword))));
    }

    @Override
    public void setCategory(Long articleId, Long categoryId) {

        boolean categoryNotExist = !categoryRepository.exists(categoryId);
        boolean articleNotExist = !articleRepository.exists(articleId);
        if (categoryNotExist || articleNotExist) {
            return;
        }

        Article article = articleRepository.findOne(articleId);
        Category category = categoryRepository.findOne(categoryId);
        article.setCategory(category);
        articleRepository.save(article);
    }

    @Override
    public void addTag(Long articleId, Long... tagsId) {
        boolean articleNotExist = !articleRepository.exists(articleId);
        if (articleNotExist) {
            return;
        }

        Article article = articleRepository.findOne(articleId);

        for (Long tagId : tagsId) {
            boolean tagNotExist = !tagRepository.exists(tagId);
            if (tagNotExist) {
                continue;
            }
            Tag tag = tagRepository.findOne(tagId);
            article.addTag(tag);
        }
        articleRepository.save(article);
    }
}
