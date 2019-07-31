package pers.geolo.guitarworld.network.callback;

import pers.geolo.guitarworld.entity.FileListener;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/31
 */
public class FileCallback<T> extends BaseCallback<T> {

    private FileListener<T> listener;

    public FileCallback(FileListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onSuccess(T t) {
        listener.onFinish(t);
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
