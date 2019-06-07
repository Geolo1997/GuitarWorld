package pers.geolo.guitarworld.http.retrofit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import pers.geolo.guitarworld.http.HttpRequest;
import pers.geolo.guitarworld.util.GenericUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public class RetrofitCallback implements Callback<String> {


    private HttpRequest httpRequest;

    public RetrofitCallback(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        JSONObject jsonObject = JSON.parseObject(response.body());
        if (response.isSuccessful() && jsonObject.getIntValue("code") == 0) {
            if (httpRequest.getiSuccess() != null) {
                Class<?> type = GenericUtils.getActualGenericImplemented(httpRequest.getiSuccess().getClass(), 0,
                        0);
                httpRequest.getiSuccess()
                        .onSuccess(JSON.parseObject(jsonObject.getString("data"), type));
            }
        } else {
            if (httpRequest.getiError() != null) {
                httpRequest.getiError().onError(jsonObject.getIntValue("code"), jsonObject.getString("message"));
            }
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (httpRequest.getiFailure() != null) {
            httpRequest.getiFailure().onFailure();
        }
    }
}
