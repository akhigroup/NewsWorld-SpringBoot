package pl.pwr.news.model.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pwr.news.model.article.Article;

import javax.persistence.*;

/**
 * Created by Evelan on 06/05/16.
 */
@Getter
@Setter
@Entity
@Table(name = "reports")
@NoArgsConstructor
public class Report {

    private static final long serialVersionUID = 2312343423243L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_fk")
    private Article article = new Article();
}
