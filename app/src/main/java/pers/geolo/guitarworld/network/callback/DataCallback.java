package pers.geolo.guitarworld.network.callback;

import pers.geolo.guitarworld.entity.DataListener;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public abstract class DataCallback<T> extends BaseCallback<T> {

    private DataListener<T> listener;

    public DataCallback(DataListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onError(int errorCode, String errorMessage) {
        listener.onError(errorMessage);
    }

    @Override
    public void onFailure() {
        listener.onError("网络错误");
    }
}
