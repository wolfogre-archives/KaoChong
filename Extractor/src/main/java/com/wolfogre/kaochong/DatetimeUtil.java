package com.wolfogre.kaochong;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by wolfogre on 9/13/16.
 */
@Service
public class DatetimeUtil {
    DateFormat dateFormat;

    public DatetimeUtil() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    public Date getDate(String formatString) {
        try {
            return dateFormat.parse(formatString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
