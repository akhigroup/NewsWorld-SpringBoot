package pl.pwr.news.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jf on 5/2/16.
 */
public class DateConverter {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date convertFromString(String dateToConvert) throws ParseException {
        Date convertedDate = dateFormat.parse(dateToConvert);
        return convertedDate;
    }
}