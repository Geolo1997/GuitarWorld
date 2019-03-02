package pers.geolo.guitarworld.network;

import android.util.Log;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<ResponseBody<T>> {

    private final String TAG = "CallBack";

    @Override
    public final void onResponse(Call<ResponseBody<T>> call, Response<ResponseBody<T>> response) {
        ResponseBody<T> responseBody = response.body();
        Headers headers = response.headers();

        if (responseBody == null) {
            Log.d(TAG, "网络错误");
            onFailure();
            return;
        }
        Log.d(TAG, "收到网络消息：" + responseBody.toString());
        int code = responseBody.getCode();
        if (code == 0) {
            Log.d(TAG, "onSuccess()");
            onSuccess(responseBody.getData());
        } else if (code == 1) {
            String message = responseBody.getMessage();
            Log.d(TAG, "onError():" + message);
            onError(message);
        }
    }

    @Override
    public final void onFailure(Call<ResponseBody<T>> call, Throwable t) {
        Log.d(TAG, "网络错误");
        onFailure();
    }

    public abstract void onSuccess(T data);


    public abstract void onError(String message);


    public abstract void onFailure();
}
