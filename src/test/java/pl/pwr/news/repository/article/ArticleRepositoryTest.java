package pl.pwr.news.repository.article;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.news.ApplicationTests;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.tag.TagRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.data.jpa.domain.Specifications.where;
import static pl.pwr.news.Constants.NAME;
import static pl.pwr.news.Constants.LINK;
import static pl.pwr.news.repository.article.ArticleSpecification.keywordInText;
import static pl.pwr.news.repository.article.ArticleSpecification.keywordInTitle;


/**
 * Created by jf on 4/14/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTests.class)
@Transactional
public class ArticleRepositoryTest {

    private static Tag tag = new Tag(NAME);
    private static Article article0 = new Article();
    private static Article article1 = new Article();
    private static Article article2 = new Article();
    private static final List<Article> ARTICLE_LIST_TAGS = Collections.singletonList(article0);
    private static final List<Article> ARTICLE_LIST_FILTER = Collections.singletonList(article2);

    static {
        article0.addTag(tag);
        article1.setLink(LINK);
        article2.setText(NAME);
    }

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void findByTags_Name_existingTagAndArticles_listOfArticlesWithTagsReturned() {
        tagRepository.save(tag);
        articleRepository.save(article0);
        assertEquals(ARTICLE_LIST_TAGS, articleRepository.findByTags_Name(NAME));
    }

    @Test
    public void findByLink_existingArticleLink_articleWithLinkReturned() {
        articleRepository.save(article1);
        assertEquals(article1, articleRepository.findByLink(LINK));
    }

    @Test
    public void findAll_filterKeywordLink_listOfArticlesReturned() {
        articleRepository.save(article2);
        assertEquals(ARTICLE_LIST_FILTER, articleRepository.findAll(where(keywordInTitle(NAME)).or(where(keywordInText(NAME)))));
    }
}