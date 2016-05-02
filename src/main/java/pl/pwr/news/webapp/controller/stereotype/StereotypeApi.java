package pl.pwr.news.webapp.controller.stereotype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.factory.StereotypeFactory;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.service.stereotype.StereotypeService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.article.dto.ArticleDTO;
import pl.pwr.news.webapp.controller.stereotype.dto.StereotypeDTO;
import pl.pwr.news.webapp.controller.stereotype.dto.ListedStereotypeDTO;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * Created by jakub on 3/9/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class StereotypeApi {

    @Autowired
    StereotypeService stereotypeService;

    @Autowired
    StereotypeFactory stereotypeFactory;

    @RequestMapping(value = "/stereotype", method = RequestMethod.GET)
    public Response<List<ListedStereotypeDTO>> getStereotypes() {

        List<Stereotype> stereotypeList = stereotypeService.findAll();
        List<ListedStereotypeDTO> stereotypeDTOList = ListedStereotypeDTO.getList(stereotypeList);

        return new Response<>(stereotypeDTOList);
    }


    @RequestMapping(value = "/stereotype/articles/{stereotypeId}", method = RequestMethod.GET)
    public Response<List<ArticleDTO>> getArticlesFromStereotype(@PathVariable("stereotypeId") Long stereotypeId) {

        if (!stereotypeService.exist(stereotypeId)) {
            return new Response<>("-1", "Stereotype not found");
        }
        Stereotype stereotype = stereotypeService.findById(stereotypeId);
        List<Article> articleList = new ArrayList<Article>(stereotype.getArticles());
        List<ArticleDTO> articleDTOList = ArticleDTO.getList(articleList);
        return new Response<>(articleDTOList);
    }

    @RequestMapping(value = "/stereotype/{stereotypeId}", method = RequestMethod.GET)
    public Response<StereotypeDTO> getStereotype(@PathVariable("stereotypeId") Long stereotypeId) {

        if (!stereotypeService.exist(stereotypeId)) {
            return new Response<>("-1", "Stereotype not found");
        }

        StereotypeDTO stereotypeDTO = new StereotypeDTO(stereotypeService.findById(stereotypeId));

        return new Response<>(stereotypeDTO);
    }

    @RequestMapping(value = "/stereotype", method = RequestMethod.POST)
    public Response<Stereotype> saveStereotype(
            @RequestParam String name) {
        Stereotype stereotype = stereotypeFactory.getInstance(name);
        stereotypeService.createStereotype(stereotype);
        return new Response<>(stereotype);
    }

    @RequestMapping(value = "/stereotype", method = RequestMethod.PUT)
    public Response<Stereotype> updateStereotype(
            @RequestParam Long stereotypeId,
            @RequestParam(required = false) String name) {

        if (!stereotypeService.exist(stereotypeId)) {
            return new Response<>("-1", "Stereotype not found");
        }

        Stereotype stereotype = stereotypeService.findById(stereotypeId);

        if (isNotBlank(name)) {
            stereotype.setName(name);
        }

        stereotypeService.updateStereotype(stereotype);
        return new Response<>(stereotype);
    }
}
