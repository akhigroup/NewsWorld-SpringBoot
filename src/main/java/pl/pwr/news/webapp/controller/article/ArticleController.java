package pl.pwr.news.webapp.controller.article;

import com.google.common.base.Splitter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.article.ArticleNotExist;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.article.NotUniqueArticle;
import pl.pwr.news.service.category.CategoryNotExist;
import pl.pwr.news.service.category.CategoryService;
import pl.pwr.news.service.tag.NotUniqueTag;
import pl.pwr.news.service.tag.TagNotExist;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.webapp.controller.article.form.AddArticleForm;

import java.util.List;
import java.util.Set;

import static org.elasticsearch.common.lang3.StringUtils.isNotBlank;

/**
 * Created by jakub on 3/22/16.
 */
@Controller
@RequestMapping(value = "/admin/article/")
@Log4j
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/list")
    public String listArticles(Model model) {
        List<Article> articleList = articleService.findAll();
        model.addAttribute("articleList", articleList);
        return "article/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createNewArticle(Model model) {
        List<Tag> tagList = tagService.findAll();
        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("tagList", tagList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("addArticleForm", new AddArticleForm());
        return "article/add-edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editArticle(@RequestParam("id") Long articleId, Model model) {
        Article article = null;
        try {
            article = articleService.findById(articleId);
        } catch (ArticleNotExist articleNotExist) {
            articleNotExist.printStackTrace();
        }
        List<Tag> allTags = tagService.findAll();
        List<Category> categoryList = categoryService.findAll();
        AddArticleForm addArticleForm = new AddArticleForm(article);

        if (article != null) {
            allTags.removeAll(article.getTags());
        }

        assert article != null;
        model.addAttribute("checkedTags", article.getTags());
        model.addAttribute("tagList", allTags);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("addArticleForm", addArticleForm);
        return "article/add-edit";
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public String createOrUpdateArticle(@ModelAttribute(value = "addArticleForm") AddArticleForm addArticleForm) {
        Article articleToProcess = addArticleForm.getArticle();

        //TODO no i zajebiście, z kiepskiego kodu wyszedł nam chujowy :D
        try {
            articleToProcess = articleService.create(articleToProcess);
        } catch (NotUniqueArticle notUniqueArticle) {
            notUniqueArticle.printStackTrace();
            try {
                articleToProcess = articleService.update(articleToProcess);
            } catch (ArticleNotExist articleNotExist) {
                articleNotExist.printStackTrace();
            }
        }
        Long articleId = articleToProcess.getId();
        String tagsInString = addArticleForm.getTagsInString();

        if (isNotBlank(tagsInString)) {
            Iterable<String> checkedTags = Splitter.on(',')
                    .trimResults()
                    .omitEmptyStrings()
                    .split(tagsInString);

            Set<Tag> articleTags = articleToProcess.getTags();
            for (Tag tag : articleTags) {
                Long tagId = tag.getId();
                try {
                    articleService.removeTag(articleId, tagId);
                } catch (ArticleNotExist | TagNotExist articleNotExist) {
                    articleNotExist.printStackTrace();
                }
            }

            for (String tag : checkedTags) {
                Tag createdTag = null;
                try {
                    createdTag = tagService.createOrGetExisting(new Tag(tag));
                } catch (NotUniqueTag notUniqueTag) {
                    notUniqueTag.printStackTrace();
                }
                try {
                    if (createdTag != null) {
                        articleService.addTag(articleId, createdTag.getId());
                    }
                } catch (ArticleNotExist | TagNotExist articleNotExist) {
                    articleNotExist.printStackTrace();
                }
            }
        }

        Long selectedCategoryId = addArticleForm.getCategoryId();
        if (selectedCategoryId != null) {
            try {
                articleService.addCategory(articleId, selectedCategoryId);
            } catch (ArticleNotExist | CategoryNotExist articleNotExist) {
                articleNotExist.printStackTrace();
            }
        }

        return "redirect:/admin/article/list";
    }
}
