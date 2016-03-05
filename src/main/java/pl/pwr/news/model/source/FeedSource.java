package pl.pwr.news.model.source;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pwr.news.model.article.Article;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jakub on 2/29/16.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "feed_sources")
public class FeedSource implements Serializable {

    private static final long serialVersionUID = 13234253243243L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;

    @Column(unique = true)
    private String link;

    private boolean enabled = true;
    private Long addedDate;
    private Long lastChecked;

    @OneToMany
    private Set<Article> articles = new HashSet<>();

    public FeedSource(String name, String link) {
        this.name = name;
        this.link = link;
    }

}
