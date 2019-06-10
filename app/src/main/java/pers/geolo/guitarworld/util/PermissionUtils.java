package pers.geolo.guitarworld.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-04
 */
public class PermissionUtils {

    private static List<Callback> permissionCallbackHolder = new ArrayList<>();

    public static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String[] permissions, Callback callback) {
        permissionCallbackHolder.add(callback);
        ActivityCompat.requestPermissions(activity, permissions, permissionCallbackHolder.size() - 1);
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults) {
        Callback callback = permissionCallbackHolder.get(requestCode);
        permissionCallbackHolder.add(requestCode, null);
        if (callback != null) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    callback.onFailure(permissions, grantResults);
                    return;
                }
            }
            callback.onSuccess(permissions, grantResults);
        }
    }

    public interface Callback {

        void onSuccess(String[] permissions, int[] grantResults);

        void onFailure(String[] permissions, int[] grantResults);
    }
}
