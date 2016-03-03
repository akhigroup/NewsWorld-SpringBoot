package pl.pwr.news.model.article;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.news.model.category.Category;
import pl.pwr.news.model.tag.Tag;

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
@Table(name = "articles")
public class Article implements Serializable {

    private static final long serialVersionUID = 132243253243243L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(name = "image_url")
    private String imageUrl;

    private String link;

    private boolean visible = false;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    private Category category;

    private Long addedDate;
}
