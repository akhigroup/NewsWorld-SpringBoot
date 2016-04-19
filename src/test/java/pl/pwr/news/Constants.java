package pl.pwr.news;

import pl.pwr.news.model.user.UserRole;

import java.util.Collections;

/**
 * Created by jf on 4/18/16.
 */
public class Constants {

    public final static String REST_CONTENT_TYPE = "application/json;charset=UTF-8";
    public final static String RESULT = "result";
    public final static String STATUS_OK = "000";
    public final static String STATUS_NOT_FOUND = "-1";

    public static final Long ID = 1L;
    public static final Long COUNT = 1L;
    public static final String NAME = "testName";
    public static final String IMAGE_URL = "testImageUrl";
    public static final String LINK = "http://test.link";
    public static final String USERNAME = "testUsernme";
    public static final String EMAIL = "testEmail@test.com";
    public static final String ACTIVATION_HASH = "testActivationHash";
    public static final String TOKEN = "testToken";
    public static final UserRole ROLE = UserRole.USER;
    public final static String TEXT = "text";
    public final static String TITLE = "title";
    public static final boolean EXISTS = true;
    public static final boolean NOT_EXISTS = false;
    public static final Iterable<Long> IDS = Collections.singletonList(ID);
    public static final String NAME_PARAM = "name";
    public static final String CATEGORY_ID_PARAM = "categoryId";
    public static final String IMAGE_URL_PARAM = "imageUrl";


}
