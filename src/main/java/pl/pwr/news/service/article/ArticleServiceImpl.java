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
    public Article createOrUpdate(Article article) {
        Optional<Long> articleId = Optional.ofNullable(article.getId());

        if (!articleRepository.exists(articleId.orElse(-1L))) {
            article.setAddedDate(new Date());
        }
        return articleRepository.save(article);
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
    public Page<Article> findPopular(int page, int pageSize) {
        return articleRepository.findAll(new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "views")));
    }

    @Override
    public Page<Article> findMostLiked(int page, int pageSize) {
        return articleRepository.findAll(new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "likes")));
    }

    @Override
    public List<Article> findByTag(String tagName) {
        return articleRepository.findByTags_Name(tagName);
    }

    @Override
    public Article findById(Long id) {
        Article article = articleRepository.findOne(id);
        article.incrementViews();
        articleRepository.save(article);
        return articleRepository.findOne(id);
    }

    /**
     * @param likeArticleId - article to like
     * @return Returns all likes of current article
     */
    public Long likeArticle(Long likeArticleId) {
        Article article = articleRepository.findOne(likeArticleId);
        article.incrementLikes();
        articleRepository.save(article);
        return article.getLikes();
    }

    /**
     * @param dislikeArticleId - article to dislike
     * @return Returns all dislikes of current article
     */
    public Long dislikeArticle(Long dislikeArticleId) {
        Article article = articleRepository.findOne(dislikeArticleId);
        article.incrementDislikes();
        articleRepository.save(article);
        return article.getDislikes();
    }


    @Override
    public List<Article> findAll(String keyword, String link) {
        return articleRepository.findAll(where(keywordInTitle(keyword)).or(where(keywordInText(keyword))));
    }

    @Override
    public void addCategory(Long articleId, Long... categoryIds) {
        assignArticleTo(articleId, categoryRepository, Article::setCategory, categoryIds);
    }

    @Override
    public void addTag(Long articleId, Long... tagIds) {
        assignArticleTo(articleId, tagRepository, Article::addTag, tagIds);
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
