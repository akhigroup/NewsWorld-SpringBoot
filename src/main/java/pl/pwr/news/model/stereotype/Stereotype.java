package pl.pwr.news.model.stereotype;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pwr.news.model.tag.Tag;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by falfasin on 5/11/16.
 */
@Getter
@Setter
@Entity
@Table(name = "stereotypes")
@NoArgsConstructor
public class Stereotype {

    private static final long serialVersionUID = 2312393423243L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "stereotypes_tags",
            joinColumns = @JoinColumn(name = "stereotype_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public Stereotype(String name) {
        this.name = name;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }
}