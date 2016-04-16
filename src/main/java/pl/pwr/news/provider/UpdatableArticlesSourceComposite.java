package pl.pwr.news.provider;

import pl.pwr.news.model.article.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by jf on 3/5/16.
 */
public class UpdatableArticlesSourceComposite implements UpdatableArticlesSource {

    private final List<UpdatableArticlesSource> sources = new ArrayList<>();

    public UpdatableArticlesSourceComposite() {}

    public UpdatableArticlesSourceComposite(List<UpdatableArticlesSource> sources) {
        this.sources.addAll(sources);
    }

    public void addSource(UpdatableArticlesSource source) {
        if (sources.contains(source)) {
            return;
        }
        sources.add(source);
    }

    public boolean removeSource(UpdatableArticlesSource source) {
        return sources.remove(source);
    }

    @Override
    public Future<List<Article>> update() {
        CompletableFuture<List<Article>> promise = new CompletableFuture<>();
        new Thread(() -> {
            List<Article> fromAllSources = new ArrayList<>();
            for (UpdatableArticlesSource source : sources) {
                try {
                    List<Article> newItems = source.update().get();
                    if (newItems != null) {
                        fromAllSources.addAll(newItems);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            Collections.shuffle(fromAllSources);
            promise.complete(fromAllSources);
        }).start();
        return promise;
    }
}
