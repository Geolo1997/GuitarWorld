package pers.geolo.guitarworld.ui.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import java.util.HashMap;
import java.util.Map;

import pers.geolo.android.app.GuideActivity;
import pers.geolo.guitarworld.ui.temp.PermissionCallback;
import pers.geolo.guitarworld.ui.temp.PermissionRequestCode;
import pers.geolo.guitarworld.view.base.LoadingView;
import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;
import pers.geolo.util.SingletonHolder;

public abstract class BaseActivity extends GuideActivity implements ToastView, RefreshView, LoadingView {

    protected final String TAG = this.getClass().getSimpleName();
    private final Map<PermissionRequestCode, PermissionCallback[]> permissionCallbackMap = new HashMap<>();

    public boolean havePermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    protected abstract int getContentView();

    @Override
    protected int getContentViewId() {
        return getContentView();
    }

    /**
     * 请求多个permission
     *
     * @param requestCode
     * @param permissions
     * @param callbacks
     */
    public void requestPermissions(PermissionRequestCode requestCode, String[] permissions,
                                   PermissionCallback[] callbacks) {
        permissionCallbackMap.put(requestCode, callbacks);
        ActivityCompat.requestPermissions(this, permissions, requestCode.ordinal());
    }

    /**
     * 请求单个permission
     *
     * @param requestCode
     * @param permission
     * @param callback
     */
    public void requestPermission(PermissionRequestCode requestCode, String permission, PermissionCallback callback) {
        permissionCallbackMap.put(requestCode, new PermissionCallback[]{callback});
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode.ordinal());
    }

    /**
     * permission请求回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionRequestCode permissionRequestCode = PermissionRequestCode.values()[requestCode];
        PermissionCallback[] callbacks = permissionCallbackMap.get(permissionRequestCode);
        if (callbacks != null) {
            for (int i = 0; i < grantResults.length && i < callbacks.length; i++) {
                PermissionCallback callback = callbacks[i];
                if (callback != null) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure();
                    }
                }
            }
        }
    }

    public void setFragment(int viewId, Class<? extends Fragment> fragmentClass) {
        super.setFragment(viewId, SingletonHolder.getInstance(fragmentClass));
    }

    public void setFragment(int viewId, Class<? extends Fragment> fragmentClass, Bundle parameters) {
        super.setFragment(viewId, SingletonHolder.getInstance(fragmentClass), parameters);
    }

    public void startActivityAndFinish(Class<? extends Activity> activityClass) {
        super.startActivity(activityClass);
        finish();
    }

    public void showLoading() {
        super.showLoading("加载中");
    }
}
