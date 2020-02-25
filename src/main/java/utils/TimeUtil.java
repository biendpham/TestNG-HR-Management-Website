package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();

    public static Date toDate(String dateString, String... pattern) {
        try {
            if (pattern.length > 0) {
                DATE_FORMAT.applyPattern(pattern[0]);
            } else {
                DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
            }
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            DATE_FORMAT.applyPattern(pattern[0]);
        } else {
            DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        }

        if (date == null) {
            date = new Date();
        }
        return DATE_FORMAT.format(date);
    }

    public static String getCurrentTime() {
    	return toString(new Date(), "dd/MM/yyyy HH:mm:ss");
    }
    
}
