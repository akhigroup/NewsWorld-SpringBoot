package pl.pwr.news.service.category;

/**
 * Created by Evelan on 18/04/16.
 */
public class CategoryNotExist extends Exception {

    public CategoryNotExist(Long message) {
        super(String.valueOf(message));
    }

    public CategoryNotExist(String message) {
        super(message);
    }
}
