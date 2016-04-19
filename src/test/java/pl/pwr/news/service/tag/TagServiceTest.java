package pl.pwr.news.service.tag;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.tag.TagRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static pl.pwr.news.Constants.*;

/**
 * Created by jf on 4/16/16.
 */
public class TagServiceTest {

    private static Tag tag = new Tag(NAME);
    private static final List<Tag> TAG_LIST = Collections.singletonList(tag);

    @InjectMocks
    TagServiceImpl tagService;

    @Mock
    TagRepository tagRepository;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTag_nonExistingTag_newAddedTagReturned() {
        when(tagRepository.findByName(NAME)).thenReturn(null);
        when(tagRepository.save(tag)).thenReturn(tag);
        Tag tag = tagService.createTag(TagServiceTest.tag);
        verify(tagRepository, times(1)).findByName(NAME);
        verify(tagRepository, times(1)).save(TagServiceTest.tag);
        verifyNoMoreInteractions(tagRepository);
        assertEquals(tag, TagServiceTest.tag);
    }

    @Test
    public void createTag_existingTag_oldExistingTagReturned() {
        when(tagRepository.findByName(NAME)).thenReturn(tag);
        Tag createdTag = tagService.createTag(TagServiceTest.tag);
        verify(tagRepository, times(1)).findByName(NAME);
        verifyNoMoreInteractions(tagRepository);
        assertEquals(tag, createdTag);
    }

    @Test
    public void findAll_existingTags_listOfAllTagsReturned() {
        when(tagRepository.findAll()).thenReturn(TAG_LIST);
        List<Tag> tagList = tagService.findAll();
        verify(tagRepository, times(1)).findAll();
        verifyNoMoreInteractions(tagRepository);
        assertEquals(TAG_LIST, tagList);
    }

    @Test
    public void findAllById_existingTagIds_listOfAllTagsWithIdsReturned() {
        when(tagRepository.findAll(IDS)).thenReturn(TAG_LIST);
        List<Tag> tagList = tagService.findAll(IDS);
        verify(tagRepository, times(1)).findAll(IDS);
        verifyNoMoreInteractions(tagRepository);
        assertEquals(TAG_LIST, tagList);
    }

    @Test
    public void findById_existingTagId_tagWithIdReturned() {
        when(tagRepository.findOne(ID)).thenReturn(tag);
        Tag foundTag = tagService.findById(ID);
        verify(tagRepository, times(1)).findOne(ID);
        verifyNoMoreInteractions(tagRepository);
        assertEquals(tag, foundTag);
    }

    @Test
    public void findByName_existingTagName_tagWithNameReturned() {
        when(tagRepository.findByName(NAME)).thenReturn(tag);
        Tag foundTag = tagService.findByName(NAME);
        verify(tagRepository, times(1)).findByName(NAME);
        verifyNoMoreInteractions(tagRepository);
        assertEquals(tag, foundTag);
    }

    @Test
    public void exists_existingTag_trueReturned() {
        when(tagRepository.exists(ID)).thenReturn(EXISTS);
        boolean exists = tagService.exist(ID);
        verify(tagRepository, times(1)).exists(ID);
        verifyNoMoreInteractions(tagRepository);
        assertEquals(EXISTS, exists);
    }

    @Test
    public void countAll_existingTags_countOfTagsReturned() {
        when(tagRepository.count()).thenReturn(1L);
        Long count = tagService.countAll();
        verify(tagRepository, times(1)).count();
        verifyNoMoreInteractions(tagRepository);
        assertEquals(COUNT, count);
    }
}
