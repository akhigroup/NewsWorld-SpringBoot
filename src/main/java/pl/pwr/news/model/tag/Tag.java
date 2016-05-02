package pl.pwr.news.model.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
import pl.pwr.news.model.stereotype.Stereotype;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jakub on 2/29/16.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tags")
public class Tag {

    private static final long serialVersionUID = 2312343243243L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private Set<Article> articles = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Stereotype stereotype;

    public Tag(String name) {
        this.name = name;
    }
}
