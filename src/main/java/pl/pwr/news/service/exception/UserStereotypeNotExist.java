package pl.pwr.news.service.exception;

/**
 * Created by jf on 5/7/16.
 */
public class UserStereotypeNotExist extends Exception {

    public UserStereotypeNotExist(String message) {
        super(message);
    }

    public UserStereotypeNotExist(Long id) {
        super(String.valueOf(id));
    }
}
