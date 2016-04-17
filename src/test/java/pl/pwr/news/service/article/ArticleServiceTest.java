//package pl.pwr.news.service.article;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import pl.pwr.news.model.article.Article;
//import pl.pwr.news.model.category.Category;
//import pl.pwr.news.model.tag.Tag;
//import pl.pwr.news.repository.article.ArticleRepository;
//import pl.pwr.news.repository.category.CategoryRepository;
//import pl.pwr.news.repository.tag.TagRepository;
//import pl.pwr.news.service.category.CategoryServiceImpl;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
///**
// * Created by jf on 4/16/16.
// */
//public class ArticleServiceTest {
//
//    private static final String NAME = "testName";
//    private static final Long ID = 1L;
//    private static final Long COUNT = 1L;
//    private static Article article = new Article();
//    private static final List<Article> ARTICLE_LIST = Collections.singletonList(article);
//
//    @InjectMocks
//    ArticleServiceImpl articleService;
//
//    @Mock
//    ArticleRepository articleRepository;
//
//    @Mock
//    CategoryRepository categoryRepository;
//
//    @Mock
//    TagRepository tagRepository;
//
//    @Before
//    public void setup() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//
////    @Test
////    public void createTag_nonExistingTag_newAddedTagReturned() {
////        when(categoryRepository.findByName(NAME)).thenReturn(null);
////        when(categoryRepository.save(category)).thenReturn(category);
////        Category createdCategory = categoryService.createCategory(category);
////        verify(categoryRepository, times(1)).findByName(NAME);
////        verify(categoryRepository, times(1)).save(category);
////        verifyNoMoreInteractions(categoryRepository);
////        assertEquals(category, createdCategory);
////    }
////
////    @Test
////    public void createTag_existingTag_oldExistingTagReturned() {
////        when(categoryRepository.findByName(NAME)).thenReturn(category);
////        Category createdCategory = categoryService.createCategory(category);
////        verify(categoryRepository, times(1)).findByName(NAME);
////        verifyNoMoreInteractions(categoryRepository);
////        assertEquals(category, createdCategory);
////    }
////
////    @Test
////    public void findAll_existingArticles_listOfAllArticlesReturned() {
////        when(articleRepository.findAll()).thenReturn(ARTICLE_LIST);
////        List<Article> articleList = articleService.findAll();
////        verify(articleRepository, times(1)).findAll();
////        verifyNoMoreInteractions(articleRepository);
////        assertEquals(ARTICLE_LIST, articleList);
////    }
////
////    @Test
////    public void likeArticle_existingArticle_listOfAllTagsWithIdsReturned() {
////        when(categoryRepository.findAll(IDS)).thenReturn(CATEGORY_LIST);
////        List<Category> tagList = categoryService.findAll(IDS);
////        verify(categoryRepository, times(1)).findAll(IDS);
////        verifyNoMoreInteractions(categoryRepository);
////        assertEquals(CATEGORY_LIST, tagList);
////    }
////
////    @Test
////    public void findById_existingArticleId_articleWithIdReturned() {
////        when(articleRepository.findOne(ID)).thenReturn(article);
////        Article foundArticle = articleService.findById(ID);
////        verify(articleRepository, times(1)).findOne(ID);
////        verifyNoMoreInteractions(articleRepository);
////        assertEquals(article, foundArticle);
////    }
////
////    @Test
////    public void findByTag_existingTagName_listOfArticlesWithTagReturned() {
////        when(articleRepository.findByTags_Name(NAME)).thenReturn(ARTICLE_LIST);
////        List<Article> articleList = articleService.findByTag(NAME);
////        verify(articleRepository, times(1)).findByTags_Name(NAME);
////        verifyNoMoreInteractions(categoryRepository);
////        assertEquals(ARTICLE_LIST, articleList);
////    }
////}
//
//
////TODO: testy po wejściu commita https://github.com/evelan/NewsWorldSpring/commit/139ee92724b2988076f545f7378ada93c47efe87