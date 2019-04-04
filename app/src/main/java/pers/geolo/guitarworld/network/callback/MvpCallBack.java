package pers.geolo.guitarworld.network.callback;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;

import pers.geolo.guitarworld.network.ResponseBody;
import pers.geolo.guitarworld.view.base.BaseView;

public abstract class MvpCallBack<T> extends BaseCallback<T> {

    private BaseView baseView;

    public MvpCallBack(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void onResponse(Call<ResponseBody<T>> call, Response<ResponseBody<T>> response) {
        if (baseView == null) {
            Log.d(TAG, "视图销毁，回调取消");
            return;
        }
        super.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<ResponseBody<T>> call, Throwable t) {
        if (baseView == null) {
            Log.d(TAG, "视图销毁，回调取消");
            return;
        }
        super.onFailure(call, t);
    }
}
