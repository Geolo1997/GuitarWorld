package pers.geolo.guitarworld.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.TextView;

public interface UIHelper {

    void startActivity(Class<? extends Activity> activityClass);

    void startActivityAndFinish(Class<? extends Activity> activityClass);

    void setFragment(int viewId, Class<?extends Fragment> fragmentClass);

    void showToast(String message);

    void showLongToast(String message);
}
