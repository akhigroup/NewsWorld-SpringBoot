package pl.pwr.news.webapp.controller.tag;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.pwr.news.factory.TagFactory;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.tag.TagService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.pwr.news.webapp.controller.Constants.*;

/**
 * Created by jf on 4/17/16.
 */

public class TagApiTest {

    private static final String NAME = "name";
    private static final Long ID = 1L;
    private static final boolean EXISTS = true;
    private static final boolean NOT_EXISTS = false;
    private static Tag tag = new Tag(NAME);
    private MockMvc mockMvc;

    @Mock
    TagService tagService;

    @Mock
    TagFactory tagFactory;

    @InjectMocks
    TagApi tagApi;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tagApi).build();
    }

    @Test
    public void getTags_existingTags_listOfTagsReturned() throws Exception {
        mockMvc.perform(get("/api/tag"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(tagService, times(1)).findAll();
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void getTag_existingTagId_tagWithIdReturned() throws Exception {
        when(tagService.exist(ID)).thenReturn(EXISTS);
        mockMvc.perform(get("/api/tag/{tagId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(tagService, times(1)).exist(ID);
        verify(tagService, times(1)).findById(ID);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void getTag_nonExistingTagId_tagNotFound() throws Exception {
        when(tagService.exist(ID)).thenReturn(NOT_EXISTS);
        mockMvc.perform(get("/api/tag/{tagId}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_NOT_FOUND)));
        verify(tagService, times(1)).exist(ID);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    public void saveTag_validTag_tagCreated() throws Exception {
        when(tagFactory.getInstance(NAME)).thenReturn(tag);
        mockMvc.perform(post("/api/tag")
                .param(NAME, NAME))
                .andExpect(status().isOk())
                .andExpect(content().contentType(REST_CONTENT_TYPE))
                .andExpect(jsonPath(RESULT, is(STATUS_OK)));
        verify(tagFactory, times(1)).getInstance(NAME);
        verify(tagService, times(1)).createTag(tag);
        verifyNoMoreInteractions(tagService, tagFactory);
    }
}
