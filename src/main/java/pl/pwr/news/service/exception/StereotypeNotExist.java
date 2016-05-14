package pl.pwr.news.service.exception;

/**
 * Created by jf on 5/11/16.
 */
public class StereotypeNotExist extends Exception {

    public StereotypeNotExist(String message) {
        super(message);
    }

    public StereotypeNotExist(Long id) {
        super(String.valueOf(id));
    }
}
