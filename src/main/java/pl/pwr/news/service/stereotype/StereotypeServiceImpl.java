package pl.pwr.news.service.stereotype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.stereotype.StereotypeRepository;
import pl.pwr.news.repository.tag.TagRepository;
import pl.pwr.news.service.exception.StereotypeNotExist;

import java.util.List;
import java.util.Optional;

/**
 * Created by falfasin on 5/12/16.
 */
@Service
public class StereotypeServiceImpl implements StereotypeService {

    @Autowired
    StereotypeRepository stereotypeRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Stereotype> findAll() {
        return (List<Stereotype>) stereotypeRepository.findAll();
    }

    @Override
    public boolean exist(Long stereotypeId) {
        return stereotypeRepository.exists(stereotypeId);
    }

    @Override
    public Stereotype findById(Long stereotypeId) {
        return stereotypeRepository.findOne(stereotypeId);
    }

    @Override
    public Stereotype create(Stereotype stereotype) {
        Optional<Stereotype> stereotypeOptional = Optional.ofNullable(stereotypeRepository.findByName(stereotype.getName()));
        if (stereotypeOptional.isPresent()) {
            return stereotypeOptional.get();
        }
        return stereotypeRepository.save(stereotype);
    }

    @Override
    public Stereotype update(Stereotype stereotype) throws StereotypeNotExist {
        Optional<Long> stereotypeId = Optional.ofNullable(stereotype.getId());
        if (!stereotypeRepository.exists(stereotypeId.orElse(-1L))) {
            throw new StereotypeNotExist("Stereotype not exist");
        }
        return stereotypeRepository.save(stereotype);
    }

    @Override
    public Stereotype findByName(String name) {
        return stereotypeRepository.findByName(name);
    }

    @Override
    public void addTag(Long stereotypeId, Long... tagIds) {
        if (!stereotypeRepository.exists(stereotypeId)) {
            return;
        }
        Stereotype stereotype = stereotypeRepository.findOne(stereotypeId);

        for (Long tagId : tagIds) {
            if (!tagRepository.exists(tagId)) {
                continue;
            }
            Tag tag = tagRepository.findOne(tagId);
            stereotype.addTag(tag);
        }
        stereotypeRepository.save(stereotype);
    }
}
