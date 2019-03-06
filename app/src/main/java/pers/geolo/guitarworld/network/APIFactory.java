package pers.geolo.guitarworld.network;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import pers.geolo.util.SingletonHolder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class APIFactory {

    private static final long DEFAULT_TIME_OUT = 1;
    private Retrofit retrofit;
    private static String baseUrl = "http://192.168.3.13:8080/GuitarWorld/";

    public static void setBaseUrl(String baseUrl) {
        APIFactory.baseUrl = baseUrl;
    }

    public APIFactory() {
        //gson converter
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//连接 超时时间
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//写操作 超时时间
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//读操作 超时时间
                .retryOnConnectionFailure(true)//错误重连
                .addInterceptor(new BaseInterceptor())
                .cookieJar(new BaseCookieJar())
                .build();
        return okHttpClient;
    }

    public <T> T getAPI(Class<T> clazz) {
        return retrofit.create(clazz);
    }

}
