package pl.pwr.news.model.user;

/**
 * Created by evelan on 12/19/15.
 */
public enum Gender {
    MALE, FEMALE, UNKNOWN;

    public static Gender getEnum(String name) {
        switch (name) {
            case "M":
                return MALE;
            case "F":
                return FEMALE;
            default:
                return UNKNOWN;
        }
    }
}
