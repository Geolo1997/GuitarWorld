package pers.geolo.guitarworld.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import butterknife.ButterKnife;
import java.util.HashMap;
import java.util.Map;

import pers.geolo.guitarworld.ui.temp.ActivityCallback;
import pers.geolo.guitarworld.ui.temp.ActivityRequestCode;
import pers.geolo.guitarworld.ui.temp.PermissionCallback;
import pers.geolo.guitarworld.ui.temp.PermissionRequestCode;
import pers.geolo.guitarworld.view.base.LoadingView;
import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;
import pers.geolo.util.SingletonHolder;

public abstract class BaseActivity extends AppCompatActivity implements ToastView, RefreshView, LoadingView {

    protected final String TAG = this.getClass().getSimpleName();
    private final Map<ActivityRequestCode, ActivityCallback> activityCallbackMap = new HashMap<>();
    private final Map<PermissionRequestCode, PermissionCallback[]> permissionCallbackMap = new HashMap<>();

    protected abstract int getContentView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
    }

    public void addActivityRequest(ActivityRequestCode requestCode, ActivityCallback callback) {
        activityCallbackMap.put(requestCode, callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ActivityRequestCode activityRequestCode = ActivityRequestCode.values()[requestCode];
        ActivityCallback callback = activityCallbackMap.get(activityRequestCode);
        if (callback != null) {
            if (resultCode == RESULT_OK) {
                callback.onSuccess(data);
            } else {
                callback.onFailure();
            }
        }
    }

    public boolean havePermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 请求多个permission
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


    public void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    public void startActivityAndFinish(Class<? extends Activity> activityClass) {
        startActivity(activityClass);
        finish();
    }

    public void setFragment(int viewId, Class<? extends BaseFragment> fragmentClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(viewId, SingletonHolder.getInstance(fragmentClass));
        transaction.commit();
    }

    public void setFragment(int viewId, Class<? extends BaseFragment> fragmentClass, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        BaseFragment baseFragment = SingletonHolder.getInstance(fragmentClass);
        baseFragment.setArguments(bundle);
        transaction.replace(viewId, baseFragment);
        transaction.commit();
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRefreshing() {

    }

    @Override
    public void hideRefreshing() {

    }
}
