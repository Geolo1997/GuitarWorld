package pers.geolo.guitarworld.presenter;

import pers.geolo.guitarworld.view.base.BaseView;

public class BasePresenter<V extends BaseView> {

    public static final String TAG = BasePresenter.class.getSimpleName();

    private V view;

    public void bind(V view) {
        this.view = view;
    }

    public void unBind() {
        this.view = null;
    }

    public V getView() {
        return view;
    }
}
