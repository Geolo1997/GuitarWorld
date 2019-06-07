package pers.geolo.guitarworld.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import pers.geolo.android.util.ActivityCollector;
import pers.geolo.guitarworld.util.ActivityUtils;
import pers.geolo.util.SingletonHolder;

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder unbinder;

    protected abstract int getContentView();


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void setFragment(int viewId, Class<? extends Fragment> fragmentClass) {
        setFragment(viewId, SingletonHolder.getInstance(fragmentClass));
    }

    public void setFragment(int viewId, Class<? extends Fragment> fragmentClass, Bundle parameters) {
        setFragment(viewId, SingletonHolder.getInstance(fragmentClass), parameters);
    }

    public void startActivityAndFinish(Class<? extends Activity> activityClass) {
        ActivityUtils.startActivity(this, activityClass);
        finish();
    }


    public void setFragment(int viewId, Fragment fragment, Bundle parameters) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment.setArguments(parameters);
        transaction.replace(viewId, fragment);
        transaction.commit();
    }

    public void setFragment(int viewId, Fragment fragment) {
        setFragment(viewId, fragment, null);
    }

    public Bundle getStartParameters() {
        Intent intent = getIntent();
        return intent.getExtras();
    }

    public void setResponseParameters(Bundle parameters) {
        Intent intent = new Intent();
        intent.putExtras(parameters);
        setResult(RESULT_OK, intent);
    }

    public void finishLastActivity() {
        ActivityCollector.finishLastActivity(this);
    }
}
