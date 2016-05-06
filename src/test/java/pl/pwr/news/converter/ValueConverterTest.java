package pl.pwr.news.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jf on 4/18/16.
 */
public class ValueConverterTest {

    private static String nameToConvert = "   nA Me \n\t";
    private static final String CONVERTED_TAG_NAME = "name";
    private static final String CONVERTED_CATEGORY_NAME = "nA Me";

    @Test
    public void convertTagName_validName_convertedNameReturned() {
        String convertedName = ValueConverter.convertTagName(nameToConvert);
        assertEquals(CONVERTED_TAG_NAME, convertedName);
    }

    @Test
    public void convertCategoryName_validName_convertedNameReturned() {
        String convertedName = ValueConverter.convertCategoryName(nameToConvert);
        assertEquals(CONVERTED_CATEGORY_NAME, convertedName);
    }
}
