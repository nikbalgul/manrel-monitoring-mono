package com.manrel.manrelmonitoringmono.util;

import java.util.Calendar;

public class DateUtils {

    public static void setZeroTime(Calendar firstDayOfYear) {
        firstDayOfYear.set(Calendar.HOUR_OF_DAY, 0);
        firstDayOfYear.set(Calendar.MINUTE, 0);
        firstDayOfYear.set(Calendar.SECOND, 0);
        firstDayOfYear.set(Calendar.MILLISECOND, 0);
    }
}
