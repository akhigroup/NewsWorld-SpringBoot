package pl.pwr.news.model.stereotype;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import pl.pwr.news.model.userstereotype.UserStereotype;
import pl.pwr.news.model.tag.Tag;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jakub on 2/29/16.
 */
@Getter
@Setter
@Entity
@Table(name = "stereotypes")
@NoArgsConstructor
public class Stereotype {

    private static final long serialVersionUID = 2312343243243L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stereotype")
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "stereotype", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<UserStereotype> userStereotypes = new HashSet<>();

    public Stereotype(String name) {
        this.name = name;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addUserStereotype(UserStereotype userStereotype) {
        this.userStereotypes.add(userStereotype);
    }
}
