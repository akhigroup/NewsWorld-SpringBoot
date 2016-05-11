package pl.pwr.news.service.exception;

/**
 * Created by jf on 5/11/16.
 */
public class UserTagNotExist extends Exception {

    public UserTagNotExist(String message) {
        super(message);
    }

    public UserTagNotExist(Long id) {
        super(String.valueOf(id));
    }
}
