package pl.pwr.news.model.category;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.article.Article;

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
public class Category {

    private static final long serialVersionUID = 2312343243243L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(unique = true)
    private String name;

    @OneToMany
    private Set<Article> articles = new HashSet<>();

}
