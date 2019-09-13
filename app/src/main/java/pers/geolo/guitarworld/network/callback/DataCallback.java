package pers.geolo.guitarworld.network.callback;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public class DataCallback<T> extends BaseCallback<T> {

    private pers.geolo.guitarworld.entity.DataCallback<T> listener;

    public DataCallback(pers.geolo.guitarworld.entity.DataCallback<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onSuccess(T t) {
        listener.onReturn(t);
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
