package pl.pwr.news.webapp.controller.article;

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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.pwr.news.Constants.*;

/**
 * Created by Evelan-E6540 on 06.03.2016.
 */
public class ArticleApiTest {

    private static Article article = new Article();
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
    public void getArticle_existingArticleId_ArticleWithIdReturned() throws Exception {
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
}
//TODO testy do ArticleApi