package io.wabm.supermarket.misc.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by liu on 2016-11-25 .
 */
public class Theage {
        public static int caculation(Date birthDate) {
            int age=0;
           SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
            String birth_year=format_y.format(birthDate);
          //  format.setTimeZone(calendar.getTimeZone());


            Calendar now = Calendar.getInstance();
            int a=now.get(Calendar.YEAR);

            age=a-Integer.parseInt(birth_year);
            return age;
        }
}
