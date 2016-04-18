package pl.pwr.news.service.tag;

/**
 * Created by Evelan on 18/04/16.
 */
public class TagNotExist extends Exception {

    public TagNotExist(Long message) {
        super(String.valueOf(message));
    }

    public TagNotExist(String message) {
        super(message);
    }
}
