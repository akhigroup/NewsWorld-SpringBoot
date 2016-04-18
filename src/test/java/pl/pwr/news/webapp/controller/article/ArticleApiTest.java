package pl.pwr.news.webapp.controller.article;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.webapp.controller.article.ArticleApi;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Evelan-E6540 on 06.03.2016.
 */
public class ArticleApiTest {

    private final static String REST_CONTENT_TYPE = "application/json;charset=UTF-8";
    private final static String TEXT = "text";
    private final static String TITLE = "title";
    private final static String RESULT = "result";
    private final static String STATUS_OK = "000";
    private final static Long ID = 1L;
    private final static int PAGE_SIZE = 1;
    private final static int PAGE = 1;
    private static Article article = new Article();
    private static Article article1 = new Article();
    private static final List<Article> articleList = Arrays.asList(article, article1);
    private MockMvc mockMvc;

    static {
        article.setText(TEXT);
        article.setTitle(TITLE);
        article.setId(ID);
    }
    @Mock
    ArticleService articleService;

    @Mock
    CategoryService categoryService;

    @Mock
    TagService tagService;


    @InjectMocks
    ArticleApi articleApi;


    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(articleApi).build();
    }

    @Test
    public void testGetArticle() throws Exception {

        when(articleService.findById(ID)).thenReturn(article);
        when(articleService.incrementViews(ID)).thenReturn(1L);

        mockMvc.perform(get("/api/article/{articleId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)))
                .andExpect(jsonPath("$.value.text", is(TEXT)))
                .andExpect(jsonPath("$.value.title", is(TITLE)));
        verify(articleService, times(1)).findById(ID);
        verify(articleService, times(1)).incrementViews(ID);
        verifyNoMoreInteractions(articleService);
    }

    @Ignore
    @Test
    public void testGetArticles() throws Exception {
        PageRequest pageRequest = new PageRequest(PAGE_SIZE, PAGE);
        when(articleService.findAll(pageRequest)).thenReturn(new PageImpl<>(articleList));

        mockMvc.perform(get("/api/article"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)))
                .andExpect(jsonPath("$.value.pageSize", is(PAGE_SIZE)))
                .andExpect(jsonPath("$.value.page", is(PAGE)));
        verify(articleService, times(1)).findAll(pageRequest);
        verifyNoMoreInteractions(articleService);
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