package pers.geolo.guitarworld.microview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/9/5
 */
public abstract class XmlViewController implements ViewController {

    private View rootView;

    @Override
    public View createView(ViewGroup container) {
        if (rootView == null) {
            rootView = LayoutInflater.from(container.getContext())
                    .inflate(getLayout(), container, false);
        }
        return rootView;
    }

    @Override
    public View getView() {
        return rootView;
    }

    protected abstract int getLayout();
}
