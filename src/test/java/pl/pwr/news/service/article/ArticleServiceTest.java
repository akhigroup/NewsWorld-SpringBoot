package pl.pwr.news.service.article;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.article.ArticleRepository;
import pl.pwr.news.repository.category.CategoryRepository;
import pl.pwr.news.repository.tag.TagRepository;
import pl.pwr.news.service.exception.ArticleNotExist;
import pl.pwr.news.service.exception.NotUniqueArticle;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static pl.pwr.news.Constants.*;

/**
 * Created by jf on 4/16/16.
 */
public class ArticleServiceTest {

    private static final Long ID_UPDATE = -1L;
    private static Article article = new Article();
    private static Category CATEGORY = new Category(NAME);
    private static Tag TAG = new Tag(NAME);
    private static final List<Article> ARTICLE_LIST = Collections.singletonList(article);

    static {
        article.setLink(LINK);
    }

    @InjectMocks
    ArticleServiceImpl articleService;

    @Mock
    ArticleRepository articleRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    TagRepository tagRepository;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_existingArticles_listOfAllArticlesReturned() {
        when(articleRepository.findAll()).thenReturn(ARTICLE_LIST);
        List<Article> articleList = articleService.findAll();
        verify(articleRepository, times(1)).findAll();
        verifyNoMoreInteractions(articleRepository);
        assertEquals(ARTICLE_LIST, articleList);
    }

    @Test
    public void likeArticle_existingArticle_articleLikesReturned() throws ArticleNotExist {
        when(articleRepository.exists(ID)).thenReturn(EXISTS);
        when(articleRepository.findOne(ID)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(article);
        Long likes = articleService.likeArticle(ID);
        verify(articleRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository);
        assertEquals(article.getLikes().longValue(), likes.longValue());
    }

    @Test(expected = ArticleNotExist.class)
    public void likeArticle_nonExistingArticle_exceptionThrown() throws ArticleNotExist {
        when(articleRepository.exists(ID)).thenReturn(NOT_EXISTS);
        articleService.likeArticle(ID);
        verify(articleRepository, times(1)).exists(ID);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void dislikeArticle_existingArticle_articleDisikesReturned() throws ArticleNotExist {
        when(articleRepository.exists(ID)).thenReturn(EXISTS);
        when(articleRepository.findOne(ID)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(article);
        Long dislikes = articleService.dislikeArticle(ID);
        verify(articleRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository);
        assertEquals(article.getDislikes().longValue(), dislikes.longValue());
    }

    @Test(expected = ArticleNotExist.class)
    public void dislikeArticle_nonExistingArticle_exceptionThrown() throws ArticleNotExist {
        when(articleRepository.exists(ID)).thenReturn(NOT_EXISTS);
        articleService.dislikeArticle(ID);
        verify(articleRepository, times(1)).exists(ID);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void incrementViews_existingArticle_articleViewsReturned() throws ArticleNotExist {
        when(articleRepository.exists(ID)).thenReturn(EXISTS);
        when(articleRepository.findOne(ID)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(article);
        Long views = articleService.incrementViews(ID);
        verify(articleRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository);
        assertEquals(article.getViews().longValue(), views.longValue());
    }

    @Test(expected = ArticleNotExist.class)
    public void incrementViews_nonExistingArticle_exceptionThrown() throws ArticleNotExist {
        when(articleRepository.exists(ID)).thenReturn(NOT_EXISTS);
        articleService.incrementViews(ID);
        verify(articleRepository, times(1)).exists(ID);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void findById_existingArticleId_articleWithIdReturned() {
        when(articleRepository.findOne(ID)).thenReturn(article);
        Article foundArticle = articleService.findById(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verifyNoMoreInteractions(articleRepository);
        assertEquals(article, foundArticle);
    }

    @Test
    public void findByTag_existingTagName_listOfArticlesWithTagReturned() {
        when(articleRepository.findByTags_Name(NAME)).thenReturn(ARTICLE_LIST);
        List<Article> articleList = articleService.findByTag(NAME);
        verify(articleRepository, times(1)).findByTags_Name(NAME);
        verifyNoMoreInteractions(categoryRepository);
        assertEquals(ARTICLE_LIST, articleList);
    }

    @Test
    public void unique_existingArticleLink_falseReturned() {
        when(articleRepository.findByLink(LINK)).thenReturn(article);
        boolean unique = articleService.unique(LINK);
        verify(articleRepository, times(1)).findByLink(LINK);
        verifyNoMoreInteractions(articleRepository);
        assertFalse(unique);
    }

    @Test
    public void unique_nonExistingArticleLink_trueReturned() {
        when(articleRepository.findByLink(LINK)).thenReturn(null);
        boolean unique = articleService.unique(LINK);
        verify(articleRepository, times(1)).findByLink(LINK);
        verifyNoMoreInteractions(articleRepository);
        assertTrue(unique);
    }

    @Test
    public void countAll_existingArticles_countOfArticlesReturned() {
        when(articleRepository.count()).thenReturn(COUNT);
        Long count = articleService.countAll();
        verify(articleRepository, times(1)).count();
        verifyNoMoreInteractions(articleRepository);
        assertEquals(COUNT.longValue(), count.longValue());
    }

    @Test
    public void create_nonExistingArticle_newAddedArticleReturned() throws NotUniqueArticle {
        when(articleRepository.save(article)).thenReturn(article);
        when(articleRepository.findByLink(LINK)).thenReturn(null);
        Article addedArticle = articleService.create(article);
        verify(articleRepository, times(1)).save(article);
        verify(articleRepository, times(1)).findByLink(LINK);
        verifyNoMoreInteractions(articleRepository);
        assertEquals(article, addedArticle);
        assertNotNull(article.getAddedDate());
    }

    @Test(expected = NotUniqueArticle.class)
    public void create_existingArticle_exceptionThrown() throws NotUniqueArticle {
        when(articleRepository.findByLink(LINK)).thenReturn(article);
        Article addedArticle = articleService.create(article);
        verify(articleRepository, times(1)).findByLink(LINK);
        verifyNoMoreInteractions(articleRepository);
        assertNull(addedArticle);
    }

    @Test
    public void update_existingArticle_updatedArticleReturned() throws ArticleNotExist {
        when(articleRepository.exists(ID_UPDATE)).thenReturn(EXISTS);
        when(articleRepository.save(article)).thenReturn(article);
        Article updatedArticle = articleService.update(article);
        verify(articleRepository, times(1)).exists(ID_UPDATE);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository);
        assertEquals(article, updatedArticle);
    }

    @Test(expected = ArticleNotExist.class)
    public void update_nonExistingArticle_exceptionThrown() throws ArticleNotExist {
        when(articleRepository.exists(ID)).thenReturn(NOT_EXISTS);
        Article updatedArticle = articleService.update(article);
        verify(articleRepository, times(1)).exists(ID);
        verifyNoMoreInteractions(articleRepository);
        assertNull(updatedArticle);
    }

    @Test
    public void addCategory_nonExistingArticleId_returned() {
        article.setCategory(null);
        when(articleRepository.exists(ID)).thenReturn(NOT_EXISTS);
        articleService.addCategory(ID, ID);
        verify(articleRepository, times(1)).exists(ID);
        verifyNoMoreInteractions(articleRepository);
        assertNull(article.getCategory());
    }

    @Test
    public void addCategory_nonExistingCategoryId_returned() {
        article.setCategory(null);
        when(articleRepository.exists(ID)).thenReturn(EXISTS);
        when(articleRepository.findOne(ID)).thenReturn(article);
        when(categoryRepository.exists(ID)).thenReturn(NOT_EXISTS);
        when(articleRepository.save(article)).thenReturn(article);
        articleService.addCategory(ID, ID);
        verify(articleRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verify(categoryRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository, categoryRepository);
        assertNull(article.getCategory());
    }

    @Test
    public void addCategory_existingArticleId_categoriesAddedToArticle() {
        article.setCategory(null);
        when(articleRepository.exists(ID)).thenReturn(EXISTS);
        when(articleRepository.findOne(ID)).thenReturn(article);
        when(categoryRepository.exists(ID)).thenReturn(EXISTS);
        when(categoryRepository.findOne(ID)).thenReturn(CATEGORY);
        when(articleRepository.save(article)).thenReturn(article);
        articleService.addCategory(ID, ID);
        verify(articleRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verify(categoryRepository, times(1)).exists(ID);
        verify(categoryRepository, times(1)).findOne(ID);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository, categoryRepository);
        assertEquals(CATEGORY, article.getCategory());
    }

    @Test
    public void addTag_nonExistingArticleId_returned() {
        when(articleRepository.exists(ID)).thenReturn(NOT_EXISTS);
        articleService.addTag(ID, ID);
        verify(articleRepository, times(1)).exists(ID);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void addTag_nonExistingTagIds_returned() {
        article.setTags(new HashSet<Tag>());
        when(articleRepository.exists(ID)).thenReturn(EXISTS);
        when(articleRepository.findOne(ID)).thenReturn(article);
        when(tagRepository.exists(ID)).thenReturn(NOT_EXISTS);
        when(articleRepository.save(article)).thenReturn(article);
        articleService.addTag(ID, ID);
        verify(articleRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verify(tagRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository, tagRepository);
        assertTrue(article.getTags().isEmpty());
    }

    @Test
    public void addTag_existingArticleId_tagsAddedToArticle() {
        article.setTags(new HashSet<Tag>());
        when(articleRepository.exists(ID)).thenReturn(EXISTS);
        when(articleRepository.findOne(ID)).thenReturn(article);
        when(tagRepository.exists(ID)).thenReturn(EXISTS);
        when(tagRepository.findOne(ID)).thenReturn(TAG);
        when(articleRepository.save(article)).thenReturn(article);
        articleService.addTag(ID, ID);
        verify(articleRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verify(tagRepository, times(1)).exists(ID);
        verify(tagRepository, times(1)).findOne(ID);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository, tagRepository);
        assertTrue(article.getTags().contains(TAG));
    }

    @Test
    public void removeTag_nonExistingArticleId_returned() {
        when(articleRepository.exists(ID)).thenReturn(NOT_EXISTS);
        articleService.removeTag(ID, ID);
        verify(articleRepository, times(1)).exists(ID);
        verifyNoMoreInteractions(articleRepository);
    }

    @Test
    public void removeTag_nonExistingTagIds_returned() {
        article.addTag(TAG);
        when(articleRepository.exists(ID)).thenReturn(EXISTS);
        when(articleRepository.findOne(ID)).thenReturn(article);
        when(tagRepository.exists(ID)).thenReturn(NOT_EXISTS);
        when(articleRepository.save(article)).thenReturn(article);
        articleService.removeTag(ID, ID);
        verify(articleRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verify(tagRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository, tagRepository);
        assertTrue(article.getTags().contains(TAG));
    }

    @Test
    public void removeTag_existingArticleId_tagsAddedToArticle() {
        article.addTag(TAG);
        when(articleRepository.exists(ID)).thenReturn(EXISTS);
        when(articleRepository.findOne(ID)).thenReturn(article);
        when(tagRepository.exists(ID)).thenReturn(EXISTS);
        when(tagRepository.findOne(ID)).thenReturn(TAG);
        when(articleRepository.save(article)).thenReturn(article);
        articleService.removeTag(ID, ID);
        verify(articleRepository, times(1)).exists(ID);
        verify(articleRepository, times(1)).findOne(ID);
        verify(tagRepository, times(1)).exists(ID);
        verify(tagRepository, times(1)).findOne(ID);
        verify(articleRepository, times(1)).save(article);
        verifyNoMoreInteractions(articleRepository, tagRepository);
        assertTrue(article.getTags().isEmpty());
    }
}