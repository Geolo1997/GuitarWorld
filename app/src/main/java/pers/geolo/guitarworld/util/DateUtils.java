package pers.geolo.guitarworld.util;


import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String toString(Date date) {
        return toString(date, "yyyy-MM-dd HH:mm:ss");
    }

    @SuppressLint("SimpleDateFormat")
    public static String toString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
