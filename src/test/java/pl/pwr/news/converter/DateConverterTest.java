package pl.pwr.news.converter;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by jf on 5/2/16.
 */
public class DateConverterTest {

    private static final String validDate = "1000-10-10";
    private static final String convertedDateToString  = "Thu Oct 10 00:00:00 CET 1000";
    private static final String invalidDate = "invalidDate";

    @Test
    public void convertedDate_validDate_convertedDateReturned() throws ParseException {
        Date convertedDate = DateConverter.convertFromString(validDate);
        assertEquals(convertedDateToString, convertedDate.toString());
    }

    @Test(expected = ParseException.class)
    public void convertedDate_invalidDate_convertedNameReturned() throws ParseException {
        Date convertedDate = DateConverter.convertFromString(invalidDate);
        assertNull(convertedDate);
    }
}