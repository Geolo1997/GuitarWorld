package pers.geolo.guitarworld.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class APIFactory {

    private static final long DEFAULT_TIME_OUT = 1;
    private Retrofit retrofit;

    public APIFactory() {
        String baseUrl = "http://192.168.1.107:8080/GuitarWorld/";
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//连接 超时时间
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//写操作 超时时间
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//读操作 超时时间
                .retryOnConnectionFailure(true)//错误重连
                .cookieJar(new BaseCookieJar())
                .build();
        return okHttpClient;
    }


    public <T> T getAPI(Class<T> clazz) {
        return retrofit.create(clazz);
    }

}
