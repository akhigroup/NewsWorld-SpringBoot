package pl.pwr.news.repository.article;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.news.ApplicationTests;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.tag.Tag;
//import pl.pwr.news.repository.TestRepositoryConfig;
import pl.pwr.news.repository.tag.TagRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jf on 4/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTests.class)
@Transactional
public class ArticleRepositoryTest {

    private static final String NAME = "testName";
    private static Tag tag = new Tag(NAME);
    private static Article article0 = new Article();
    private static Article article1 = new Article();
    private static final List<Article> ARTICLE_LIST = Arrays.asList(article0, article1);

    static {
        article0.addTag(tag);
        article1.addTag(tag);
    }

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Before
    public void setup() {
        tagRepository.save(tag);
        articleRepository.save(article0);
        articleRepository.save(article1);
    }

    @Test
    public void findByTags_Name_existingTagAndArticles_listOfArticlesWithTagsReturned() {
        assertEquals(ARTICLE_LIST, articleRepository.findByTags_Name(NAME));
    }
}
