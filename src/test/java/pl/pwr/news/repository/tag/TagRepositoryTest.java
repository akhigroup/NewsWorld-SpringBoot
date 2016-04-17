package pl.pwr.news.repository.tag;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.news.ApplicationTests;
import pl.pwr.news.model.tag.Tag;

import static org.junit.Assert.assertEquals;

/**
 * Created by jf on 4/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTests.class)
@Transactional
public class TagRepositoryTest {

    private static final String NAME = "testName";
    private static final Tag TAG = new Tag(NAME);

    @Autowired
    TagRepository tagRepository;

    @Before
    public void setup() {
        tagRepository.save(TAG);
    }

    @Test
    public void findByName_existingTagName_tagWithNameReturned() {
        assertEquals(TAG.getId(), tagRepository.findByName(NAME).getId());
    }
}
