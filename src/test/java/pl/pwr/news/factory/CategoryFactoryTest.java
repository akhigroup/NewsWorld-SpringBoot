package pl.pwr.news.category;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pwr.news.factory.CategoryFactory;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.service.category.CategoryService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static pl.pwr.news.Constants.NAME;
import static pl.pwr.news.Constants.IMAGE_URL;

/**
 * Created by jf on 4/18/16.
 */
public class CategoryFactoryTest {

    private static Category category = new Category(NAME);

    @InjectMocks
    CategoryFactory categoryFactory;

    @Mock
    CategoryService categoryService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getInstance_existingCategoryName_oldCategoryReturned() {
        when(categoryService.findByName(NAME)).thenReturn(category);
        Category foundCategory = categoryFactory.getInstance(NAME, IMAGE_URL);
        verify(categoryService, times(1)).findByName(NAME);
        verifyNoMoreInteractions(categoryService);
        assertEquals(category, foundCategory);
    }

    @Test
    public void getInstance_nonExistingCategoryName_newCategoryReturned() {
        when(categoryService.findByName(NAME)).thenReturn(null);
        Category newCategory = categoryFactory.getInstance(NAME, IMAGE_URL);
        verify(categoryService, times(1)).findByName(NAME);
        verifyNoMoreInteractions(categoryService);
        assertNotNull(newCategory);
    }
}
