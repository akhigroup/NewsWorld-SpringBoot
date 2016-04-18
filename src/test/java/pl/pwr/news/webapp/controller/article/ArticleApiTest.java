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
import static pl.pwr.news.webapp.controller.Constants.*;

/**
 * Created by Evelan-E6540 on 06.03.2016.
 */
public class ArticleApiTest {

    private final static Long ID = 1L;
    public final static String TEXT = "text";
    public final static String TITLE = "title";
    private static Article article = new Article();
    private static Article article1 = new Article();
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


//    @RequestMapping(value = "/article", method = RequestMethod.GET)
//    public Response<List<ArticleDTO>> getArticles(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
//                                                  @RequestParam(required = false, defaultValue = DEFAULT_START_PAGE) int page) {
//        pageSize = getCorrectMaxPageSize(pageSize);
//        Page<Article> databaseArticle = articleService.findAll(new PageRequest(page, pageSize));
//        List<ArticleDTO> articleDTOList = ArticleDTO.getList(databaseArticle.getContent());
//        return new Response<>(articleDTOList);
//    }
//
//
//    @RequestMapping(value = "/article/popular", method = RequestMethod.GET)
//    public Response<List<ArticleDTO>> getPopularArticles(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
//                                                         @RequestParam(required = false, defaultValue = DEFAULT_START_PAGE) int page) {
//        pageSize = getCorrectMaxPageSize(pageSize);
//        Page<Article> databaseArticle = articleService.findPopular(page, pageSize);
//        List<ArticleDTO> articleDTOList = ArticleDTO.getList(databaseArticle.getContent());
//        return new Response<>(articleDTOList);
//    }
//
//
//    @RequestMapping(value = "/article/liked", method = RequestMethod.GET)
//    public Response<List<ArticleDTO>> getMostLikedArticles(@RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
//                                                           @RequestParam(required = false, defaultValue = DEFAULT_START_PAGE) int page) {
//        pageSize = getCorrectMaxPageSize(pageSize);
//        Page<Article> databaseArticle = articleService.findMostLiked(page, pageSize);
//        List<ArticleDTO> articleDTOList = ArticleDTO.getList(databaseArticle.getContent());
//        return new Response<>(articleDTOList);
//    }
//
//    private int getCorrectMaxPageSize(int pageSize) {
//        if (pageSize > MAX_PAGE_SIZE) {
//            pageSize = MAX_PAGE_SIZE;
//        }
//        return pageSize;
//    }
//
//
//    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
//    public Response<ArticleDTO> getArticle(@PathVariable("articleId") Long articleId) {
//        articleService.incrementViews(articleId);
//        ArticleDTO articleDTO = new ArticleDTO(articleService.findById(articleId));
//        return new Response<>(articleDTO);
//    }
//
//    @RequestMapping(value = "/article/{articleId}/like", method = RequestMethod.GET)
//    public Response<Long> likeArticle(@PathVariable("articleId") Long articleId) {
//        return new Response<>(articleService.likeArticle(articleId));
//    }
//
//    @RequestMapping(value = "/article/{articleId}/dislike", method = RequestMethod.GET)
//    public Response<Long> dislikeArticle(@PathVariable("articleId") Long articleId) {
//        return new Response<>(articleService.dislikeArticle(articleId));
//    }
//
//    @RequestMapping(value = "/article/search", method = RequestMethod.GET)
//    public Response<List<ArticleDTO>> searchArticles(@RequestParam(value = "tag") String tag) {
//        List<ArticleDTO> articleDTOList = ArticleDTO.getList(articleService.findByTag(tag));
//        return new Response<>(articleDTOList);
//    }
//
//    @RequestMapping(value = "/article", method = RequestMethod.POST)
//    public Response<Article> saveArticle(
//            @RequestParam("title") String title,
//            @RequestParam(value = "text", required = false) String text,
//            @RequestParam(value = "imageUrl", required = false) String imageUrl,
//            @RequestParam(value = "link") String link,
//            @RequestParam Long categoryId,
//            @RequestParam Long[] tagIds) {
//        Category category = categoryService.findById(categoryId);
//        List<Tag> tags = tagService.findAll(Arrays.asList(tagIds));
//        Article article = Article.builder().title(title).text(text).imageUrl(imageUrl).
//                link(link).category(category).tags(tags).addedDate(new Date()).build();
//        articleService.createOrUpdate(article);
//
//        return new Response<>(article);
//    }
//
//
//    @RequestMapping(value = "/article", method = RequestMethod.PUT)
//    public Response<Article> updateArticle(
//            @RequestParam Long articleId,
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String text,
//            @RequestParam(required = false) String imageUrl,
//            @RequestParam(required = false) String link,
//            @RequestParam(required = false) Long categoryId,
//            @RequestParam(required = false) Long[] tagIds) {
//
//        Article article = articleService.findById(articleId);
//
//        if (article == null) {
//            return new Response<>("-1", "No article with id: " + articleId + " to update", null);
//        }
//
//        if (isNotBlank(title)) {
//            article.setTitle(title);
//        }
//
//        if (isNotBlank(text)) {
//            article.setText(text);
//        }
//
//        if (isNotBlank(imageUrl)) {
//            article.setImageUrl(imageUrl);
//        }
//
//        if (isNotBlank(link)) {
//            article.setLink(link);
//        }
//
//        if (categoryId != null) {
//            Category category = categoryService.findById(categoryId);
//            article.setCategory(category);
//        }
//
//        articleService.createOrUpdate(article);
//
//        if (tagIds != null) {
//            articleService.addTag(articleId, tagIds);
//        }
//
//        article = articleService.findById(articleId);
//        return new Response<>(article);
//    }
//
//    @RequestMapping(value = "/article/search/", method = RequestMethod.GET)
//    public Response<List<ArticleDTO>> searchArticle(
//            @RequestParam(value = "keyword", required = false) String keyword) {
//
//        List<Article> articleList = articleService.findAll(keyword, "");
//        List<ArticleDTO> articleDTOList = ArticleDTO.getList(articleList);
//
//        return new Response<>(articleDTOList);
//    }
//}
