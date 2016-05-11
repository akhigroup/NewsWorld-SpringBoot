package pl.pwr.news.service.exception;

/**
 * Created by jf on 5/11/16.
 */
public class TagNotExist extends Exception {

    public TagNotExist(String message) {
        super(message);
    }

    public TagNotExist(Long id) {
        super(String.valueOf(id));
    }
}
