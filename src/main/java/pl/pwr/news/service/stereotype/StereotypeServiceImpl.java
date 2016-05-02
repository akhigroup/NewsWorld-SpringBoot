package pl.pwr.news.service.stereotype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwr.news.model.stereotype.Stereotype;
import pl.pwr.news.model.tag.Tag;
import pl.pwr.news.repository.stereotype.StereotypeRepository;
import pl.pwr.news.repository.tag.TagRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by jakub on 3/9/16.
 */
@Service
public class StereotypeServiceImpl implements StereotypeService {

    @Autowired
    StereotypeRepository stereotypeRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public Stereotype createStereotype(Stereotype stereotype) {
        Optional<Stereotype> stereotypeWithNotUniqueName = Optional.ofNullable(stereotypeRepository.findByName(stereotype.getName()));
        if (stereotypeWithNotUniqueName.isPresent()) {
            return stereotypeWithNotUniqueName.get();
        }
        return stereotypeRepository.save(stereotype);
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

    @Override
    public void updateStereotype(Stereotype stereotype) {
        Long stereotypeId = stereotype.getId();

        if (!stereotypeRepository.exists(stereotypeId)) {
            return;
        }
        //  ???
    }

    @Override
    public List<Stereotype> findAll() {
        return (List<Stereotype>) stereotypeRepository.findAll();
    }

    @Override
    public List<Stereotype> findAll(Iterable<Long> stereotypeIds) {
        return (List<Stereotype>) stereotypeRepository.findAll(stereotypeIds);
    }

    @Override
    public Stereotype findById(Long id) {
        return stereotypeRepository.findOne(id);
    }

    @Override
    public Stereotype findByName(String name) {
        return stereotypeRepository.findByName(name);
    }

    @Override
    public boolean exist(Long stereotypeId) {
        return stereotypeRepository.exists(stereotypeId);
    }

    @Override
    public Long countAll() {
        return stereotypeRepository.count();
    }
}
