package pers.geolo.guitarworld.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-04
 */
public abstract class BaseDelegate extends SwipeBackFragment {

    private Unbinder unbinder;

    public abstract Object getLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (getLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) getLayout(), container, false);
        } else if (getLayout() instanceof View) {
            rootView = (View) getLayout();
        } else {
            throw new ClassCastException("getLayout() type must be int or View!");
        }
        unbinder = ButterKnife.bind(this, rootView);
        onBindView(savedInstanceState, rootView);
        return attachToSwipeBack(rootView);
    }

    public SupportActivity getContainerActivity() {
        return (SupportActivity) _mActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
