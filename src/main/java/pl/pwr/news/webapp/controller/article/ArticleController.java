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
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.article.ArticleService;
import pl.pwr.news.service.stereotype.StereotypeService;
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
    StereotypeService stereotypeService;

    @RequestMapping(value = "/list")
    public String listArticles(Model model) {
        List<Article> articleList = articleService.findAll();
        model.addAttribute("articleList", articleList);
        return "article/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createNewArticle(Model model) {
        List<Tag> tagList = tagService.findAll();
        List<Stereotype> stereotypeList = stereotypeService.findAll();

        model.addAttribute("tagList", tagList);
        model.addAttribute("stereotypeList", stereotypeList);
        model.addAttribute("addArticleForm", new AddArticleForm());
        return "article/add-edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editArticle(@RequestParam("id") Long articleId, Model model) {
        Article article = articleService.findById(articleId);
        List<Tag> allTags = tagService.findAll();
        List<Stereotype> stereotypeList = stereotypeService.findAll();
        AddArticleForm addArticleForm = new AddArticleForm(article);

        allTags.removeAll(article.getTags());

        model.addAttribute("checkedTags", article.getTags());
        model.addAttribute("tagList", allTags);
        model.addAttribute("stereotypeList", stereotypeList);
        model.addAttribute("addArticleForm", addArticleForm);
        return "article/add-edit";
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public String createOrUpdateArticle(@ModelAttribute(value = "addArticleForm") AddArticleForm addArticleForm) {
        Article articleToProcess = addArticleForm.getArticle();
        articleToProcess = articleService.createOrUpdate(articleToProcess);
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
                articleService.removeTag(articleId, tagId);
            }

            for (String tag : checkedTags) {
                Tag createdTag = tagService.createTag(new Tag(tag));
                articleService.addTag(articleId, createdTag.getId());
            }
        }

        Long selectedStereotypeId = addArticleForm.getStereotypeId();
        if (selectedStereotypeId != null) {
            articleService.addStereotype(articleId, selectedStereotypeId);
        }

        return "redirect:/admin/article/list";
    }
}
