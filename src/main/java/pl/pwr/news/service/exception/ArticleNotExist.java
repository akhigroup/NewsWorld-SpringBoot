package pl.pwr.news.service.exception;

/**
 * Created by Evelan on 17/04/16.
 */
public class ArticleNotExist extends Exception {

    public ArticleNotExist(String message) {
        super(message);
    }

    public ArticleNotExist(Long id) {
        super(String.valueOf(id));

    }
}

