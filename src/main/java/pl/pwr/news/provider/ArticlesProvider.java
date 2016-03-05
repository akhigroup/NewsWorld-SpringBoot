package pl.pwr.news.provider;

import pl.pwr.news.model.article.Article;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * Created by jf on 3/5/16.
 *
 */

public class ArticlesProvider {

    public interface UpdateListener {
        void onUpdated(List<Article> articles);
        void onUpdateFailed();
    }

    private final UpdatableArticlesSource source;
    private final UpdateListener listener;
    private final Timer updateTimer = new Timer();

    public ArticlesProvider(UpdatableArticlesSource source,
                            UpdateListener listener) {
        this.source = source;
        this.listener = listener;
    }

    public void setAutoUpdate(long interval) {
        if (interval == 0) {
            updateTimer.cancel();
            return;
        }

        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, interval, interval);
    }

    public void update() {
        new Thread(() -> {
            try {
                List<Article> newArticles = source.update().get();
                EventQueue.invokeLater(() -> listener.onUpdated(newArticles));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                EventQueue.invokeLater(() -> listener.onUpdateFailed());
            }
        }).start();
    }
}
