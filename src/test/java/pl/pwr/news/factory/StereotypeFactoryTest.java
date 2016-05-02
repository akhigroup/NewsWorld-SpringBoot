package pl.pwr.news.stereotype;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pwr.news.factory.StereotypeFactory;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.service.stereotype.StereotypeService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static pl.pwr.news.Constants.NAME;
import static pl.pwr.news.Constants.IMAGE_URL;

/**
 * Created by jf on 4/18/16.
 */
public class StereotypeFactoryTest {

    private static Stereotype stereotype = new Stereotype(NAME);

    @InjectMocks
    StereotypeFactory stereotypeFactory;

    @Mock
    StereotypeService stereotypeService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getInstance_existingStereotypeName_oldStereotypeReturned() {
        when(stereotypeService.findByName(NAME)).thenReturn(stereotype);
        Stereotype foundStereotype = stereotypeFactory.getInstance(NAME, IMAGE_URL);
        verify(stereotypeService, times(1)).findByName(NAME);
        verifyNoMoreInteractions(stereotypeService);
        assertEquals(stereotype, foundStereotype);
    }

    @Test
    public void getInstance_nonExistingStereotypeName_newStereotypeReturned() {
        when(stereotypeService.findByName(NAME)).thenReturn(null);
        Stereotype newStereotype = stereotypeFactory.getInstance(NAME, IMAGE_URL);
        verify(stereotypeService, times(1)).findByName(NAME);
        verifyNoMoreInteractions(stereotypeService);
        assertNotNull(newStereotype);
    }
}
