package pl.pwr.news.service.user;

/**
 * Created by Evelan on 23/04/16.
 */
public class UserNotExist extends Exception {

    public UserNotExist(String message) {
        super(message);
    }
}
