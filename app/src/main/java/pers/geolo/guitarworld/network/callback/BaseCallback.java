package pers.geolo.guitarworld.network.callback;

import android.util.Log;
import okhttp3.Headers;
import pers.geolo.guitarworld.entity.DataListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<ResponseBody<T>> {

    public static final String TAG = "CallBack";

    public BaseCallback() {

    }

    private DataListener<T> listener;

    public BaseCallback(DataListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<ResponseBody<T>> call, Response<ResponseBody<T>> response) {
        ResponseBody<T> responseBody = response.body();
        Headers headers = response.headers();

        if (responseBody == null) {
            Log.d(TAG, "网络错误：" + "code: " + response.code() + " message:" + response.message());
            onFailure();
            return;
        }
        Log.d(TAG, "收到网络消息：" + responseBody.toString());
        int code = responseBody.getCode();
        if (code == 0) {
            Log.d(TAG, "success()");
            onSuccess(responseBody.getData());
        } else {
            String message = responseBody.getMessage();
            Log.d(TAG, "onError(): code=" + code + ", message=" + message);
            onError(code, message);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody<T>> call, Throwable t) {
        Log.d(TAG, "网络错误: " + t.getMessage());
        onFailure();
    }

    public abstract void onSuccess(T t);

    public abstract void onError(int errorCode, String errorMessage);

    public abstract void onFailure();

}
