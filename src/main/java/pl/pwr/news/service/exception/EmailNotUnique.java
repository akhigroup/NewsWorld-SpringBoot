package pl.pwr.news.service.exception;

/**
 * Created by Evelan on 23/04/16.
 */
public class EmailNotUnique extends Throwable {

    public EmailNotUnique(String message) {
        super(message);
    }
}
