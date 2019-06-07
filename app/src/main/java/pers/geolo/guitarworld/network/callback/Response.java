package pers.geolo.guitarworld.network.callback;

import android.content.Context;

import pers.geolo.guitarworld.ui.base.BaseActivity;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public abstract class Response<T> extends BaseCallback<T> {

    private BaseActivity context;

    public Response(BaseActivity context) {
        this.context = context;
    }

    public abstract void onResponse(T responseData);

    @Override
    public void onSuccess(T responseData) {
        onResponse(responseData);
    }

    @Override
    public void onError(int errorCode, String errorMessage) {
        context.showToast("请求错误");
    }

    @Override
    public void onFailure() {
        context.showToast("网络错误");
    }
}
