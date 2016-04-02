package pl.pwr.news.provider;

import pl.pwr.news.model.article.Article;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by jf on 3/5/16.
 *
 */
public interface UpdatableArticlesSource {
    Future<List<Article>> update();
}
