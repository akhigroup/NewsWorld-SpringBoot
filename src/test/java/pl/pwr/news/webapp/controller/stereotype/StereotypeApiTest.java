package pl.pwr.news.webapp.controller.stereotype;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pwr.news.factory.StereotypeFactory;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.service.stereotype.StereotypeService;

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
public class StereotypeApiTest {

    private static Stereotype stereotype = new Stereotype(NAME);
    private MockMvc mockMvc;

    @Mock
    StereotypeService stereotypeService;

    @Mock
    StereotypeFactory stereotypeFactory;

    @InjectMocks
    StereotypeApi stereotypeApi;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(stereotypeApi).build();
    }

    @Test
    public void getCategories_existingCategories_listOfCategoriesReturned() throws Exception {
        mockMvc.perform(get("/api/stereotype"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(stereotypeService, times(1)).findAll();
        verifyNoMoreInteractions(stereotypeService);
    }

    @Test
    public void getArticlesFromStereotype_existingStereotypeId_listOfArticlesFromStereotypeReturned() throws Exception {
        when(stereotypeService.exist(ID)).thenReturn(EXISTS);
        when(stereotypeService.findById(ID)).thenReturn(stereotype);
        mockMvc.perform(get("/api/stereotype/articles/{stereotypeId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(stereotypeService, times(1)).exist(ID);
        verify(stereotypeService, times(1)).findById(ID);
        verifyNoMoreInteractions(stereotypeService);
    }

    @Test
    public void getArticlesFromStereotype_nonExistingStereotypeId_stereotypeNotFound() throws Exception {
        when(stereotypeService.exist(ID)).thenReturn(NOT_EXISTS);
        mockMvc.perform(get("/api/stereotype/articles/{stereotypeId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_NOT_FOUND)));
        verify(stereotypeService, times(1)).exist(ID);
        verifyNoMoreInteractions(stereotypeService);
    }

    @Test
    public void getStereotype_existingStereotypeId_stereotypeWithIdReturned() throws Exception {
        when(stereotypeService.exist(ID)).thenReturn(EXISTS);
        mockMvc.perform(get("/api/stereotype/{stereotypeId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(stereotypeService, times(1)).exist(ID);
        verify(stereotypeService, times(1)).findById(ID);
        verifyNoMoreInteractions(stereotypeService);
    }

    @Test
    public void getStereotype_nonExistingStereotypeId_stereotypeNotFound() throws Exception {
        when(stereotypeService.exist(ID)).thenReturn(NOT_EXISTS);
        mockMvc.perform(get("/api/stereotype/{stereotypeId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_NOT_FOUND)));
        verify(stereotypeService, times(1)).exist(ID);
        verifyNoMoreInteractions(stereotypeService);
    }

    @Test
    public void saveStereotype_validStereotype_stereotypeCreated() throws Exception {
        when(stereotypeFactory.getInstance(NAME)).thenReturn(stereotype);
        mockMvc.perform(post("/api/stereotype")
                .param(NAME_PARAM, NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(stereotypeFactory, times(1)).getInstance(NAME);
        verify(stereotypeService, times(1)).createStereotype(stereotype);
        verifyNoMoreInteractions(stereotypeService, stereotypeFactory);
    }

    @Test
    public void updateStereotype_existingStereotypeId_stereotypeUpdated() throws Exception {
        when(stereotypeService.exist(ID)).thenReturn(EXISTS);
        when(stereotypeService.findById(ID)).thenReturn(stereotype);
        mockMvc.perform(put("/api/stereotype")
                .param(CATEGORY_ID_PARAM, String.valueOf(ID))
                .param(NAME_PARAM, NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(stereotypeService, times(1)).exist(ID);
        verify(stereotypeService, times(1)).findById(ID);
        verify(stereotypeService, times(1)).updateStereotype(stereotype);
        verifyNoMoreInteractions(stereotypeService);
    }

    @Test
    public void updateStereotype_nonExistingStereotypeId_stereotypeNotFound() throws Exception {
        when(stereotypeService.exist(ID)).thenReturn(NOT_EXISTS);
        mockMvc.perform(put("/api/stereotype")
                .param(CATEGORY_ID_PARAM, String.valueOf(ID))
                .param(NAME_PARAM, NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_NOT_FOUND)));
        verify(stereotypeService, times(1)).exist(ID);
        verifyNoMoreInteractions(stereotypeService);
    }
}
