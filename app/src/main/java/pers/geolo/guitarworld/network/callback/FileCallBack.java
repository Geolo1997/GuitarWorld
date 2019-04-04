package pers.geolo.guitarworld.network.callback;

import java.io.InputStream;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class FileCallBack implements Callback<ResponseBody> {

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            InputStream inputStream = responseBody.byteStream();
            new Thread(() -> {
                onResponseInputStream(inputStream);
            }).start();
        } else {
            int code = response.code();
            String message = response.message();
            onError(code, message);
        }
    }

    protected abstract void onResponseInputStream(InputStream inputStream);


    protected abstract void onError(int code, String message);
}
