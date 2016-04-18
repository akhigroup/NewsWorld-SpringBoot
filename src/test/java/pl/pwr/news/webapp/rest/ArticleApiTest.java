package pl.pwr.news.webapp.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.webapp.controller.article.ArticleApi;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Evelan-E6540 on 06.03.2016.
 */
public class ArticleApiTest {

    private final static String REST_CONTENT_TYPE = "application/json;charset=UTF-8";
    @Mock
    ArticleService articleService;

    @Mock
    CategoryService categoryService;

    @Mock
    TagService tagService;


    @InjectMocks
    ArticleApi articleApi;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetArticles() throws Exception {

        final String ARTICLE_TEXT = "article text";
        final String ARTICLE_TITLE = "article tiitle";
        final Long ARTICLE_ID = 1L;

        Article article = new Article();
        article.setText(ARTICLE_TEXT);
        article.setTitle(ARTICLE_TITLE);
        article.setId(ARTICLE_ID);

        when(articleService.findById(ARTICLE_ID)).thenReturn(article);
        when(articleService.incrementViews(ARTICLE_ID)).thenReturn(1L);


        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(articleApi).build();
        mockMvc.perform(get("/api/article/{articleId}", ARTICLE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath("result", is("000")))
                .andExpect(jsonPath("$.value.text", is(ARTICLE_TEXT)))
                .andExpect(jsonPath("$.value.title", is(ARTICLE_TITLE)));

        // można tutaj to jakoś fajnie użyć
//        verify(articleService, times(2)).findById(ARTICLE_ID);
//        verifyNoMoreInteractions(articleService);
    }

    @Test
    public void testGetArticle() throws Exception {

    }

    @Test
    public void testSaveArticle() throws Exception {

    }

    @Test
    public void testUpdateArticle() throws Exception {

    }

    @Test
    public void testSearchArticle() throws Exception {

    }
}