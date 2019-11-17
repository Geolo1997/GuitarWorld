package pers.geolo.guitarworld.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/19
 */
public class DateUtils {

    /**
     * 获取友好的时间字符串
     *
     * @param date
     * @return
     */
    public static String toFriendlyString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar currentCalendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_YEAR);
        int subDay = currentDay - day;
        if (subDay > 10) {
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(date);
        } else if (subDay >= 2 && subDay <= 10) {
            return subDay + "天前";
        } else if (subDay == 1) {
            return "昨天 " + new SimpleDateFormat("HH:mm").format(date);
        } else {
            return "今天 " + new SimpleDateFormat("HH:mm").format(date);
        }
    }
}
