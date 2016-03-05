package pl.pwr.news.provider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.pwr.news.model.article.Article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by jf on 3/5/16.
 */
public class RssFeed implements UpdatableArticlesSource {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String LINK = "link";
    private static final String CHANNEL = "channel";
    private static final String ITEM = "item";

    private final String url;
    private final List<Article> bufferedArticles = new ArrayList<>();

    private String channelTitle;
    private String channelDescription;
    private String channelLink;

    public RssFeed(String url) {
        this.url = url;
    }

    // Available after first update
    public String getChannelTitle() {
        return channelTitle;
    }

    // Available after first update
    public String getChannelDescription() {
        return channelDescription;
    }

    // Available after first update
    public String getChannelLink() {
        return channelLink;
    }

    public String getUrl() {
        return url;
    }

    private void extractChannelInfo(Element channel) {
        channelTitle = getTextFromElementByTagName(channel, TITLE);
        channelDescription = getTextFromElementByTagName(channel, DESCRIPTION);
        channelLink = getTextFromElementByTagName(channel, LINK);
    }

    public CompletableFuture<List<Article>> update() {
        CompletableFuture<List<Article>> promise = new CompletableFuture<>();
        new Thread(() -> {
            try {
                Document document = Jsoup.connect(url).get();

                // Multiple channels in single rss.xml are not allowed:
                // http://stackoverflow.com/a/3798880
                Element channel = document.getElementsByTag(CHANNEL).first();
                List<Article> newArticles = extractArticles(channel);
                List<Article> uniqueArticles = newArticles.stream()
                        .filter(article -> !bufferedArticles.contains(article))
                        .collect(Collectors.toList());
                bufferedArticles.addAll(uniqueArticles);
                promise.complete(uniqueArticles);
            } catch (IOException e) {
                e.printStackTrace();
                promise.complete(null);
            }
        }).start();
        return promise;
    }

    private List<Article> extractArticles(Element channel) {
        if (channelTitle == null && channelDescription == null && channelLink == null)
            extractChannelInfo(channel);

        ArrayList<Article> newArticles = new ArrayList<>();
        Elements items = channel.getElementsByTag(ITEM);
        for (Element item : items) {
            Article article = toArticle(item);
            if (article == null || bufferedArticles.contains(article)) {
                continue;
            }
            newArticles.add(article);
        }
        return newArticles;
    }

    private Article toArticle(Element item) {
        String title = getTextFromElementByTagName(item, TITLE);
        String description = getTextFromElementByTagName(item, DESCRIPTION);
        String link = getTextFromElementByTagName(item, LINK);
        if (title == null || description == null || link == null) {
            return null;
        }
        // TODO: extract image
        Article article = new Article();
        article.setTitle(title);
        article.setText(description);
        article.setLink(link);
        return article;
    }

    private String getTextFromElementByTagName(Element item, String tag) {
        Elements elements = item.getElementsByTag(tag);
        if (elements.size() == 0) {
            return null;
        }
        return elements.first().text();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RssFeed)) {
            return false;
        }
        return this.url.equals(((RssFeed)obj).url);
    }
}
