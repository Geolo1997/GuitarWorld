package pers.geolo.guitarworld.util;

import android.app.Activity;
import android.view.View;

/**
 * 状态栏工具类
 *
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/28
 */
public class StatusBarUtils {

    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
