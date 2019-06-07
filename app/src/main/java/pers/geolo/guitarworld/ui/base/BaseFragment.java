package pers.geolo.guitarworld.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pers.geolo.android.app.GuideFragment;
import pers.geolo.guitarworld.util.ActivityUtils;

public abstract class BaseFragment extends GuideFragment {

    protected final String TAG = this.getClass().getSimpleName();
    private BaseActivity activity;

    @Override
    protected int getContentViewId() {
        return getContentView();
    }


    protected abstract int getContentView();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        activity = (BaseActivity) getActivity();
        return view;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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

    @Override
    public void startActivity(Class<? extends Activity> activityClass, Bundle requestParam) {
        ActivityUtils.startActivity(getBaseActivity(), activityClass, requestParam);
    }

    @Override
    public void startActivity(Class<? extends Activity> activityClass) {
        ActivityUtils.startActivity(getBaseActivity(), activityClass);
    }

    @Override
    public void setFragment(int viewId, Fragment fragment, Bundle parameters) {
        fragment.setArguments(parameters);
        getChildFragmentManager().beginTransaction()
                .replace(viewId, fragment)
                .commit();
    }

    @Override
    public void setFragment(int viewId, Fragment fragment) {
        setFragment(viewId, fragment, null);
    }

    @Override
    public Bundle getStartParameters() {
        return getArguments();
    }

    @Override
    public void setResponseParameters(Bundle parameters) {

    }
}
