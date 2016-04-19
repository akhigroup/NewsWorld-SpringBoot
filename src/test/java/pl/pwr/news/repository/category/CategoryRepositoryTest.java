package pl.pwr.news.repository.category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.news.ApplicationTests;
import pl.pwr.news.model.category.Category;

import static org.junit.Assert.assertEquals;
import static pl.pwr.news.Constants.NAME;

/**
 * Created by jf on 4/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTests.class)
@Transactional
public class CategoryRepositoryTest {

    private static final Category CATEGORY = new Category(NAME);

    @Autowired
    CategoryRepository categoryRepository;

    @Before
    public void setup() {
        categoryRepository.save(CATEGORY);
    }

    @Test
    public void findByName_existingCategoryName_categoryWithNameReturned() {
        assertEquals(CATEGORY.getId(), categoryRepository.findByName(NAME).getId());
    }
}
