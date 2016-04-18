package pl.pwr.news.factory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.tag.TagService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by jf on 4/18/16.
 */
public class TagFactoryTest {

    private static final String NAME = "testName";
    private static Tag tag = new Tag(NAME);
    @InjectMocks
    TagFactory tagFactory;

    @Mock
    TagService tagService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getInstance_existingTagName_oldTagReturned() {
        when(tagService.findByName(NAME)).thenReturn(tag);
        Tag foundTag = tagFactory.getInstance(NAME);
        verify(tagService, times(1)).findByName(NAME);
        verifyNoMoreInteractions(tagService);
        assertEquals(tag, foundTag);
    }

    @Test
    public void getInstance_nonExistingTagName_newTagReturned() {
        when(tagService.findByName(NAME)).thenReturn(null);
        Tag newTag = tagFactory.getInstance(NAME);
        verify(tagService, times(1)).findByName(NAME);
        verifyNoMoreInteractions(tagService);
        assertNotNull(newTag);
    }
}
