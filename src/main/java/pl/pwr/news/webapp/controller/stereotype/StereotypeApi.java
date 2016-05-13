package pl.pwr.news.webapp.controller.stereotype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.pwr.news.factory.StereotypeFactory;
import pl.pwr.news.factory.TagFactory;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.service.exception.NotUniqueStereotype;
import pl.pwr.news.service.exception.StereotypeNotExist;
import pl.pwr.news.service.stereotype.StereotypeService;
import pl.pwr.news.service.tag.TagService;
import pl.pwr.news.webapp.controller.Response;
import pl.pwr.news.webapp.controller.stereotype.dto.StereotypeDTO;
import pl.pwr.news.webapp.controller.tag.dto.TagDTO;

import java.util.List;

import static org.elasticsearch.common.lang3.StringUtils.isNotBlank;

/**
 * Created by jf on 5/12/16.
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class StereotypeApi {

    @Autowired
    StereotypeService stereotypeService;

    @Autowired
    StereotypeFactory stereotypeFactory;

    @RequestMapping(value = "/stereotype", method = RequestMethod.GET)
    public Response<List<StereotypeDTO>> getStereotypes() {
        List<Stereotype> stereotypes = stereotypeService.findAll();
        List<StereotypeDTO> stereotypeDTOs = StereotypeDTO.getList(stereotypes);

        return new Response<>(stereotypeDTOs);
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
    public Response<StereotypeDTO> saveStereotype(@RequestParam String name) {
        try {
            Stereotype stereotype = stereotypeFactory.getInstance(name);
            stereotypeService.create(stereotype);
            StereotypeDTO stereotypeDTO = new StereotypeDTO(stereotype);
            return new Response<>(stereotypeDTO);
        } catch (NotUniqueStereotype notUniqueStereotype) {
            return new Response<>("-1", notUniqueStereotype.getMessage());
        }
    }

    @RequestMapping(value = "/stereotype", method = RequestMethod.PUT)
    public Response<StereotypeDTO> updateStereotype(@RequestParam Long stereotypeId,
                                                    @RequestParam(required = false) String name,
                                                    @RequestParam(required = false) Long[] tagIds) {
        Stereotype stereotype = stereotypeService.findById(stereotypeId);
        try {
            if (stereotype == null) {
                throw new StereotypeNotExist("Stereotype not exist");
            }
            if (isNotBlank(name)) {
                stereotype.setName(name);
            }
            stereotypeService.update(stereotype);
            if (tagIds != null) {
                stereotypeService.addTag(stereotypeId, tagIds);
            }
            stereotype = stereotypeService.findById(stereotypeId);
            StereotypeDTO stereotypeDTO = new StereotypeDTO(stereotype);
            return new Response<>(stereotypeDTO);
        } catch (StereotypeNotExist ex) {
            return new Response<>("-1", ex.getMessage());
        }
    }
}
