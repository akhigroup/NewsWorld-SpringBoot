package pl.pwr.news.service.tag;

/**
 * Created by Evelan on 18/04/16.
 */
public class NotUniqueTag extends Exception {

    public NotUniqueTag(String message) {
        super(message);
    }
}
