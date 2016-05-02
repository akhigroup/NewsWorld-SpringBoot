package pl.pwr.news.converter;

import com.google.common.base.CharMatcher;

import static org.apache.commons.lang.StringUtils.deleteWhitespace;

/**
 * Created by jf on 4/1/16.
 */
public class ValueConverter {

    private static final CharMatcher ALNUM = CharMatcher.inRange('a', 'z')
            .or(CharMatcher.ASCII)
            .or(CharMatcher.inRange('A', 'Z'))
            .or(CharMatcher.inRange('0', '9'))
            .precomputed();

    public static String convertTagName(String name) {
        name = name.trim();
        name = name.toLowerCase();
        name = ALNUM.retainFrom(name);
        name = deleteWhitespace(name);
        return name;
    }

    public static String convertStereotypeName(String name) {
        name = name.trim();
        name = ALNUM.retainFrom(name);
        return name;
    }
}
