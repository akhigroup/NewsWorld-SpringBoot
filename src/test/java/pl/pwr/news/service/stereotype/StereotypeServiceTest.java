package pl.pwr.news.service.stereotype;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.repository.stereotype.StereotypeRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static pl.pwr.news.Constants.NAME;
import static pl.pwr.news.Constants.ID;


/**
 * Created by jf on 4/16/16.
 */
public class StereotypeServiceTest {

    private static Stereotype stereotype = new Stereotype(NAME);
    private static final List<Stereotype> STEREOTYPE_LIST = Collections.singletonList(stereotype);
    private static final Iterable<Long> IDS = Collections.singletonList(ID);

    @InjectMocks
    StereotypeServiceImpl stereotypeService;

    @Mock
    StereotypeRepository stereotypeRepository;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createStereotype_nonExistingStereotype_newAddedStereotypeReturned() {
        when(stereotypeRepository.findByName(NAME)).thenReturn(null);
        when(stereotypeRepository.save(stereotype)).thenReturn(stereotype);
        Stereotype createdStereotype = stereotypeService.createStereotype(stereotype);
        verify(stereotypeRepository, times(1)).findByName(NAME);
        verify(stereotypeRepository, times(1)).save(stereotype);
        verifyNoMoreInteractions(stereotypeRepository);
        assertEquals(stereotype, createdStereotype);
    }

    @Test
    public void createStereotype_existingStereotype_oldExistingStereotypeReturned() {
        when(stereotypeRepository.findByName(NAME)).thenReturn(stereotype);
        Stereotype createdStereotype = stereotypeService.createStereotype(stereotype);
        verify(stereotypeRepository, times(1)).findByName(NAME);
        verifyNoMoreInteractions(stereotypeRepository);
        assertEquals(stereotype, createdStereotype);
    }

    @Test
    public void findAll_existingCategories_listOfAllCategoriesReturned() {
        when(stereotypeRepository.findAll()).thenReturn(STEREOTYPE_LIST);
        List<Stereotype> stereotypeList = stereotypeService.findAll();
        verify(stereotypeRepository, times(1)).findAll();
        verifyNoMoreInteractions(stereotypeRepository);
        assertEquals(STEREOTYPE_LIST, stereotypeList);
    }

    @Test
    public void findAllById_existingStereotypeIds_listOfAllCategoriesWithIdsReturned() {
        when(stereotypeRepository.findAll(IDS)).thenReturn(STEREOTYPE_LIST);
        List<Stereotype> stereotypeList = stereotypeService.findAll(IDS);
        verify(stereotypeRepository, times(1)).findAll(IDS);
        verifyNoMoreInteractions(stereotypeRepository);
        assertEquals(STEREOTYPE_LIST, stereotypeList);
    }

    @Test
    public void findById_existingStereotypeId_stereotypeWithIdReturned() {
        when(stereotypeRepository.findOne(ID)).thenReturn(stereotype);
        Stereotype foundStereotype = stereotypeService.findById(ID);
        verify(stereotypeRepository, times(1)).findOne(ID);
        verifyNoMoreInteractions(stereotypeRepository);
        assertEquals(stereotype, foundStereotype);
    }

    @Test
    public void findByName_existingStereotypeName_stereotypeWithNameReturned() {
        when(stereotypeRepository.findByName(NAME)).thenReturn(stereotype);
        Stereotype foundStereotype = stereotypeService.findByName(NAME);
        verify(stereotypeRepository, times(1)).findByName(NAME);
        verifyNoMoreInteractions(stereotypeRepository);
        assertEquals(stereotype, foundStereotype);
    }
    //TODO: addTag test, jeśli będzie kiedykolwiek używany
}
