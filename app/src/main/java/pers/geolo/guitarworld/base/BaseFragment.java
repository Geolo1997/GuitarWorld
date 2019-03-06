package pers.geolo.guitarworld.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import pers.geolo.guitarworld.base.UIHelper;

public abstract class BaseFragment extends Fragment implements UIHelper {

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);
    }

    @Override
    public void startActivityAndFinish(Class<? extends Activity> activityClass) {
        startActivity(activityClass);
        getActivity().finish();
    }

    @Override
    public void setFragment(int viewId, Class<? extends Fragment> fragmentClass) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
