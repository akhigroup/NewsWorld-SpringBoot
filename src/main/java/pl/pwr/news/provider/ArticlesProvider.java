package pl.pwr.news.provider;

import pl.pwr.news.model.article.Article;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by jf on 3/5/16.
 */

public class ArticlesProvider {

    private final UpdatableArticlesSource source;
    private final UpdateListener listener;

    public ArticlesProvider(UpdatableArticlesSource source, UpdateListener listener) {
        this.source = source;
        this.listener = listener;
    }

    public void update() {
        try {
            List<Article> newArticles = source.update().get();
            EventQueue.invokeLater(() -> listener.onUpdated(newArticles));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            EventQueue.invokeLater(() -> listener.onUpdateFailed());
        }
    }

    public interface UpdateListener {

        void onUpdated(List<Article> articles);
        void onUpdateFailed();
    }
}
