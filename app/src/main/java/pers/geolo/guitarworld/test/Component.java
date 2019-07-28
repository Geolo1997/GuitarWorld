package pers.geolo.guitarworld.test;

import android.view.View;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/28
 */
public interface Component {

    View getView();

    void start(Component component);

    <T> void start(Component component, Callback<T> callback);

    interface Callback<T> {
        void onReturn(T t);
    }
}
