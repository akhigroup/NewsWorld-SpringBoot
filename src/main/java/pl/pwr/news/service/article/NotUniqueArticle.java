package pl.pwr.news.service.article;

/**
 * Created by Evelan on 17/04/16.
 */
public class NotUniqueArticle extends Exception {

    public NotUniqueArticle(String message) {
        super(message);
    }
}
