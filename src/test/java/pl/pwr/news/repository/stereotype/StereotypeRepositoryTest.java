package pl.pwr.news.repository.stereotype;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.news.ApplicationTests;
import pl.pwr.news.model.stereotype.Stereotype;

import static org.junit.Assert.assertEquals;
import static pl.pwr.news.Constants.NAME;

/**
 * Created by jf on 4/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTests.class)
@Transactional
public class StereotypeRepositoryTest {

    private static final Stereotype STEREOTYPE = new Stereotype(NAME);

    @Autowired
    StereotypeRepository stereotypeRepository;

    @Before
    public void setup() {
        stereotypeRepository.save(STEREOTYPE);
    }

    @Test
    public void findByName_existingStereotypeName_stereotypeWithNameReturned() {
        assertEquals(STEREOTYPE.getId(), stereotypeRepository.findByName(NAME).getId());
    }
}
