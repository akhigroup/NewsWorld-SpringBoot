package pl.pwr.news.webapp.controller.category;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pwr.news.factory.CategoryFactory;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.service.category.CategoryService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.pwr.news.Constants.*;

/**
 * Created by jf on 4/17/16.
 */
public class CategoryApiTest {

    private static Category category = new Category(NAME);
    private MockMvc mockMvc;

    static {
        category.setImageUrl(IMAGE_URL);
    }

    @Mock
    CategoryService categoryService;

    @Mock
    CategoryFactory categoryFactory;

    @InjectMocks
    CategoryApi categoryApi;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryApi).build();
    }

    @Test
    public void getCategories_existingCategories_listOfCategoriesReturned() throws Exception {
        mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(categoryService, times(1)).findAll();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getArticlesFromCategory_existingCategoryId_listOfArticlesFromCategoryReturned() throws Exception {
        when(categoryService.exist(ID)).thenReturn(EXISTS);
        when(categoryService.findById(ID)).thenReturn(category);
        mockMvc.perform(get("/api/category/articles/{categoryId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(categoryService, times(1)).exist(ID);
        verify(categoryService, times(1)).findById(ID);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getArticlesFromCategory_nonExistingCategoryId_categoryNotFound() throws Exception {
        when(categoryService.exist(ID)).thenReturn(NOT_EXISTS);
        mockMvc.perform(get("/api/category/articles/{categoryId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_NOT_FOUND)));
        verify(categoryService, times(1)).exist(ID);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategory_existingCategoryId_categoryWithIdReturned() throws Exception {
        when(categoryService.exist(ID)).thenReturn(EXISTS);
        mockMvc.perform(get("/api/category/{categoryId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(categoryService, times(1)).exist(ID);
        verify(categoryService, times(1)).findById(ID);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void getCategory_nonExistingCategoryId_categoryNotFound() throws Exception {
        when(categoryService.exist(ID)).thenReturn(NOT_EXISTS);
        mockMvc.perform(get("/api/category/{categoryId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_NOT_FOUND)));
        verify(categoryService, times(1)).exist(ID);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void saveCategory_validCategory_categoryCreated() throws Exception {
        when(categoryFactory.getInstance(NAME, IMAGE_URL)).thenReturn(category);
        mockMvc.perform(post("/api/category")
                .param(NAME_PARAM, NAME)
                .param(IMAGE_URL_PARAM, IMAGE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(categoryFactory, times(1)).getInstance(NAME, IMAGE_URL);
        verify(categoryService, times(1)).createCategory(category);
        verifyNoMoreInteractions(categoryService, categoryFactory);
    }

    @Test
    public void updateCategory_existingCategoryId_categoryUpdated() throws Exception {
        when(categoryService.exist(ID)).thenReturn(EXISTS);
        when(categoryService.findById(ID)).thenReturn(category);
        mockMvc.perform(put("/api/category")
                .param(CATEGORY_ID_PARAM, String.valueOf(ID))
                .param(NAME_PARAM, NAME)
                .param(IMAGE_URL_PARAM, IMAGE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(categoryService, times(1)).exist(ID);
        verify(categoryService, times(1)).findById(ID);
        verify(categoryService, times(1)).updateCategory(category);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateCategory_nonExistingCategoryId_categoryNotFound() throws Exception {
        when(categoryService.exist(ID)).thenReturn(NOT_EXISTS);
        mockMvc.perform(put("/api/category")
                .param(CATEGORY_ID_PARAM, String.valueOf(ID))
                .param(NAME_PARAM, NAME)
                .param(IMAGE_URL_PARAM, IMAGE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_NOT_FOUND)));
        verify(categoryService, times(1)).exist(ID);
        verifyNoMoreInteractions(categoryService);
    }
}
