package pl.pwr.news.service.exception;

/**
 * Created by Evelan on 23/04/16.
 */
public class PasswordIncorrect extends Throwable {
    public PasswordIncorrect(String message) {
        super(message);
    }
}
