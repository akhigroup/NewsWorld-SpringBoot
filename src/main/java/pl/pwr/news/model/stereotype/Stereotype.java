package pl.pwr.news.model.stereotype;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pwr.news.model.tag.Tag;

import javax.persistence.*;
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


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Stereotype_Tags",
            joinColumns = @JoinColumn(name = "stereotype_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }
}