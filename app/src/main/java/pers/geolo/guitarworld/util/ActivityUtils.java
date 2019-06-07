package pers.geolo.guitarworld.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-04
 */
public class ActivityUtils {

    private static List<Callback> callbackHolder = new ArrayList<>();

    public static void startActivity(Activity activity, Intent intent, Bundle bundle, Callback callback) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (callback == null) {
            activity.startActivityForResult(intent, -1);
        } else {
            callbackHolder.add(callback);
            activity.startActivityForResult(intent, callbackHolder.size() - 1);
        }
    }

    public static void startActivity(Activity activity, Class<? extends Activity> activityClass, Bundle requestParam,
                                     Callback callback) {
        Intent intent = new Intent(activity, activityClass);
        startActivity(activity, intent, requestParam, callback);
    }

    public static void startActivity(Activity activity, Class<? extends Activity> activityClass, Bundle requestParam) {
        startActivity(activity, activityClass, requestParam, null);
    }

    public static void startActivity(Activity activity, Class<? extends Activity> activityClass) {
        startActivity(activity, activityClass, null, null);
    }

    protected static void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Callback callback = callbackHolder.get(requestCode);
        // 存在callback，存在返回的intent
        if (callback != null) {
            if (resultCode == Activity.RESULT_OK) { // 请求成功
                callback.onSuccess(data);
            } else { // 请求失败
                callback.onFailure(data);
            }
        }
    }

    public interface Callback {

        void onSuccess(Intent intent);

        void onFailure(Intent intent);
    }
}
