package pl.pwr.news.service.exception;

/**
 * Created by jf on 5/7/16.
 */
public class CategoryNotExist extends Exception {

    public CategoryNotExist(String message) {
        super(message);
    }

    public CategoryNotExist(Long id) {
        super(String.valueOf(id));
    }
}

