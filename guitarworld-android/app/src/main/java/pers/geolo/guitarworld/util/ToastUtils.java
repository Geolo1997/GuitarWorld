package pers.geolo.guitarworld.util;

import android.content.Context;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/26
 */
public class ToastUtils {

    public static void showInfoToast(Context context, String message) {
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT, TastyToast.INFO);
    }

    public static void showSuccessToast(Context context, String message) {
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
    }

    public static void showErrorToast(Context context, String message) {
        TastyToast.makeText(context, message, TastyToast.LENGTH_SHORT, TastyToast.ERROR);
    }
}
