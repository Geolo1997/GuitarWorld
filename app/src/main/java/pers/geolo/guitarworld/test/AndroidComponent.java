package pers.geolo.guitarworld.test;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/28
 */
public abstract class AndroidComponent implements Component {

    private View view;
    private ViewGroup parent;
    private Context context;

    @Override
    public View getView() {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(getLayout(), parent, false);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    @LayoutRes
    public abstract int getLayout();

    public abstract void init();

    @Override
    public void start(Component component) {

    }

    @Override
    public <T> void start(Component component, Callback<T> callback) {

    }
}
