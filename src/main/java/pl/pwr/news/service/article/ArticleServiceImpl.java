package pl.pwr.news.service.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.repository.article.ArticleRepository;
import pl.pwr.news.repository.category.CategoryRepository;
import pl.pwr.news.repository.tag.TagRepository;
import pl.pwr.news.service.exception.ArticleNotExist;
import pl.pwr.news.service.exception.NotUniqueArticle;

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

    /**
     * Should use: update {@link #update(Article)} OR create {@link #create(Article)}
     *
     * @param article
     * @return
     */
    @Deprecated
    @Override
    public Article createOrUpdate(Article article) {
        Optional<Long> articleId = Optional.ofNullable(article.getId());

        if (!articleRepository.exists(articleId.orElse(-1L))) {
            article.setAddedDate(new Date());
        }

        return articleRepository.save(article);
    }

    /**
     * Checks if article is unique according to source link
     *
     * @param article new article
     * @return created article in database
     * @throws NotUniqueArticle
     * @author Jakub Pomykała
     */
    @Override
    public Article create(Article article) throws NotUniqueArticle {
        article.setAddedDate(new Date());

        String articleSourceLink = article.getLink();
        if (!unique(articleSourceLink)) {
            throw new NotUniqueArticle(articleSourceLink);
        }
        return articleRepository.save(article);
    }

    /**
     * @param article aricle to update
     * @return updated article in database
     * @throws ArticleNotExist
     * @author Jakub Pomykała
     */
    @Override
    public Article update(Article article) throws ArticleNotExist {

        Optional<Long> articleId = Optional.ofNullable(article.getId());

        if (!articleRepository.exists(articleId.orElse(-1L))) {
            throw new ArticleNotExist("Article not exist");
        }
        return articleRepository.save(article);
    }

    @Override
    public List<Article> findAll() {
        return (List<Article>) articleRepository.findAll();
    }

    @Override
    public boolean unique(String sourceUrl) {
        Optional<Article> articleOptional = Optional.ofNullable(articleRepository.findByLink(sourceUrl));
        return !articleOptional.isPresent();
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
    public Page<Article> findPopular(int page, int pageSize) {
        return articleRepository.findAll(new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "views")));
    }

    @Override
    public Page<Article> findMostLiked(int page, int pageSize) {
        return articleRepository.findAll(new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "likes")));
    }

    @Override
    public Page<Article> findAllSortedByDateAsc(Pageable pageable) {
        return articleRepository.findAllByOrderByAddedDateAsc(pageable);
    }

    @Override
    public Page<Article> findAllSortedByDateAscNewerThan(Date addedDate, Pageable pageable) {
        return articleRepository.findAllByAddedDateBeforeOrderByAddedDateAsc(addedDate, pageable);
    }

    @Override
    public List<Article> findByTag(String tagName) {
        return articleRepository.findByTags_Name(tagName);
    }

    @Override
    public Article findById(Long id) {
        return articleRepository.findOne(id);
    }

    /**
     * @param id - article to like
     * @return Returns all likes of current article
     */
    public Long likeArticle(Long id) throws ArticleNotExist {

        if (!articleRepository.exists(id)) {
            throw new ArticleNotExist(id);
        }

        Article article = articleRepository.findOne(id);
        article.incrementLikes();
        articleRepository.save(article);
        return article.getLikes();
    }

    /**
     * @param id - article to dislike
     * @return Returns all dislikes of current article
     */
    public Long dislikeArticle(Long id) throws ArticleNotExist {

        if (!articleRepository.exists(id)) {
            throw new ArticleNotExist(id);
        }

        Article article = articleRepository.findOne(id);
        article.incrementDislikes();
        articleRepository.save(article);
        return article.getDislikes();
    }

    @Override
    public Long incrementViews(Long id) throws ArticleNotExist {

        if (!articleRepository.exists(id)) {
            throw new ArticleNotExist(id);
        }

        Article article = articleRepository.findOne(id);
        article.incrementViews();
        articleRepository.save(article);
        return article.getViews();
    }


    @Override
    public List<Article> findAll(String keyword, String link) {
        return articleRepository.findAll(where(keywordInTitle(keyword)).or(where(keywordInText(keyword))));
    }

    @Override
    public void addTag(Long articleId, Long... tagIds) {
        assignArticleTo(articleId, tagRepository, Article::addTag, tagIds);
    }

    @Override
    public void addCategory(Long articleId, Long categoryId) {
        assignArticleTo(articleId, categoryRepository, Article::setCategory, categoryId);
    }

    @Override
    public void removeTag(Long articleId, Long... tagIds) {
        assignArticleTo(articleId, tagRepository, Article::removeTag, tagIds);
    }


    @SuppressWarnings("unchecked")
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
}
