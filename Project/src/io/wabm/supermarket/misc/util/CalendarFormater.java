package io.wabm.supermarket.misc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class CalendarFormater {

    public static String toString(Calendar calendar) {
        return toString(calendar, "yyyy年MM月dd日");
    }

    public static String toString(Calendar calendar, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(calendar.getTimeZone());

        return format.format(calendar.getTime());
    }

}
