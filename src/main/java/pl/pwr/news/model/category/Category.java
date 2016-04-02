package pl.pwr.news.model.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pwr.news.model.article.Article;
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
@Table(name = "categories")
@NoArgsConstructor
public class Category {

    private static final long serialVersionUID = 2312343243243L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<Article> articles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

    public Category(String name) {
        this.name = name;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

}
