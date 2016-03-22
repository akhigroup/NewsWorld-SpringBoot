package pl.pwr.news.service.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
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
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public Article createOrUpdate(Article entity) {
        Optional<Long> articleId = Optional.ofNullable(entity.getId());

        if (!articleRepository.exists(articleId.orElse(-1L))) {
            entity.setAddedDate(new Date());
        }

        articleRepository.save(entity);
        return entity;
    }

    @Override
    public List<Article> findAll() {
        return (List<Article>) articleRepository.findAll();
    }

    @Override
    public Long countAll() {
        return articleRepository.count();
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public List<Article> findByTag(String tagName) {
        return articleRepository.findByTags_Name(tagName);
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
    public void addCategory(Long articleId, Long... categoriesId) {
        assignArticleTo(articleId, categoryRepository, (Article article, Category category) -> article.addCategory(category), categoriesId);
    }

    @Override
    public void addTag(Long articleId, Long... tagsId) {
        assignArticleTo(articleId, tagRepository, (Article article, Tag tag) -> article.addTag(tag), tagsId);
    }

    private <T> void assignArticleTo(Long articleId, CrudRepository repository, AssignFunction<T> assignFunction, Long... entityIds) {
        if (!articleRepository.exists(articleId)) {
            return;
        }
        Article article = articleRepository.findOne(articleId);
        for (Long entityId : entityIds) {
            if (!repository.exists(entityId)) {
                continue;
            }
            T entity = (T) repository.findOne(entityId);
            assignFunction.assignTo(article, entity);
        }
        articleRepository.save(article);
    }

    private interface AssignFunction<T> {
        void assignTo(Article article, T value);
    }
    /*
     duplikacja kodu ssie. Takie rozwiązanie odpowiada?
     stare zostawiam zakomentowane, ze zmienionym addCategory pod ManyToMany, na wszelki wypadek
    */

//    @Override
//    public void addCategory(Long articleId, Long... categoriesId) {
//        if (!articleRepository.exists(articleId)) {
//            return;
//        }
//
//        Article article = articleRepository.findOne(articleId);
//
//        for (Long categoryId : categoriesId) {
//            if (!categoryRepository.exists(categoryId)) {
//                continue;
//            }
//            Category category = categoryRepository.findOne(categoryId);
//            article.addCategory(category);
//        }
//        articleRepository.save(article);
//    }
//
//    @Override
//    public void addTag(Long articleId, Long... tagsId) {
//        if (!articleRepository.exists(articleId)) {
//            return;
//        }
//
//        Article article = articleRepository.findOne(articleId);
//
//        for (Long tagId : tagsId) {
//            if (!tagRepository.exists(tagId)) {
//                continue;
//            }
//            Tag tag = tagRepository.findOne(tagId);
//            article.addTag(tag);
//        }
//        articleRepository.save(article);
//    }
}
