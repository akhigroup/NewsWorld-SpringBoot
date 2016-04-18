package pl.pwr.news.service.category;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.repository.category.CategoryRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by jf on 4/16/16.
 */
public class CategoryServiceTest {

    private static final String NAME = "testName";
    private static final Long ID = 1L;
    private static Category category = new Category(NAME);
    private static final List<Category> CATEGORY_LIST = Collections.singletonList(category);
    private static final Iterable<Long> IDS = Collections.singletonList(ID);

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createCategory_nonExistingCategory_newAddedCategoryReturned() {
        when(categoryRepository.findByName(NAME)).thenReturn(null);
        when(categoryRepository.save(category)).thenReturn(category);
        Category createdCategory = categoryService.createCategory(category);
        verify(categoryRepository, times(1)).findByName(NAME);
        verify(categoryRepository, times(1)).save(category);
        verifyNoMoreInteractions(categoryRepository);
        assertEquals(category, createdCategory);
    }

    @Test
    public void createCategory_existingCategory_oldExistingCategoryReturned() {
        when(categoryRepository.findByName(NAME)).thenReturn(category);
        Category createdCategory = categoryService.createCategory(category);
        verify(categoryRepository, times(1)).findByName(NAME);
        verifyNoMoreInteractions(categoryRepository);
        assertEquals(category, createdCategory);
    }

    @Test
    public void findAll_existingCategories_listOfAllCategoriesReturned() {
        when(categoryRepository.findAll()).thenReturn(CATEGORY_LIST);
        List<Category> categoryList = categoryService.findAll();
        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
        assertEquals(CATEGORY_LIST, categoryList);
    }

    @Test
    public void findAllById_existingCategoryIds_listOfAllCategoriesWithIdsReturned() {
        when(categoryRepository.findAll(IDS)).thenReturn(CATEGORY_LIST);
        List<Category> categoryList = categoryService.findAll(IDS);
        verify(categoryRepository, times(1)).findAll(IDS);
        verifyNoMoreInteractions(categoryRepository);
        assertEquals(CATEGORY_LIST, categoryList);
    }

    @Test
    public void findById_existingCategoryId_categoryWithIdReturned() {
        when(categoryRepository.findOne(ID)).thenReturn(category);
        Category foundCategory = categoryService.findById(ID);
        verify(categoryRepository, times(1)).findOne(ID);
        verifyNoMoreInteractions(categoryRepository);
        assertEquals(category, foundCategory);
    }

    @Test
    public void findByName_existingCategoryName_categoryWithNameReturned() {
        when(categoryRepository.findByName(NAME)).thenReturn(category);
        Category foundCategory = categoryService.findByName(NAME);
        verify(categoryRepository, times(1)).findByName(NAME);
        verifyNoMoreInteractions(categoryRepository);
        assertEquals(category, foundCategory);
    }
    //TODO: addTag test, jeśli będzie kiedykolwiek używany, update jak zostanie naprawiony
}
