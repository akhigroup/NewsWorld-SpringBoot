package pl.pwr.news.webapp.controller.article;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.article.ArticleNotExist;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;

import javax.persistence.EntityExistsException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.common.lang3.StringUtils.isNotBlank;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.pwr.news.webapp.controller.Constants.*;

/**
 * Created by Evelan-E6540 on 06.03.2016.
 */
public class ArticleApiTest {

    private final static Long ID = 1L;
    public final static String TEXT = "text";
    public final static String TITLE = "title";
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