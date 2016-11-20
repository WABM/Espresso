package io.wabm.supermarket.misc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class CalendarFormater {

    public static String toString(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        format.setTimeZone(calendar.getTimeZone());

        return format.format(calendar.getTime());
    }

}
