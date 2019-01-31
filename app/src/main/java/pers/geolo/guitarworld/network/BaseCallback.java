package pers.geolo.guitarworld.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T> implements Callback<ResponseBody<T>> {
    @Override
    public final void onResponse(Call<ResponseBody<T>> call, Response<ResponseBody<T>> response) {
        ResponseBody<T> responseBody = response.body();
        int code = responseBody.getCode();
        if (code == 0) {
            onSuccess(responseBody.getData());
        } else if (code == 1) {
            onError(responseBody.getMessage());
        }
    }

    @Override
    public final void onFailure(Call<ResponseBody<T>> call, Throwable t) {
        onFailure();
    }

    public abstract void onSuccess(T data);


    public abstract void onError(String message);


    public abstract void onFailure();
}
