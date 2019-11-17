package pers.geolo.guitarworld.network;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import pers.geolo.android.http.DefaultCookieJar;
import pers.geolo.android.http.DefaultGson;

public class HttpClient {

    public static String baseUrl = "http://192.168.3.13:8080/GuitarWorld/";
    public static String hotPointBaseUrl = "http://192.168.43.191:8080/GuitarWorld/";

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    static {
        // okHttpClient默认配置
        okHttpClient = new OkHttpClient.Builder()
                // 连接 超时时间
                .connectTimeout(2000, TimeUnit.MILLISECONDS)
                // 写操作 超时时间
                .writeTimeout(20000, TimeUnit.MILLISECONDS)
                // 读操作 超时时间
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                // 错误重连
                .retryOnConnectionFailure(true)
                // 开启cookie
                .cookieJar(new DefaultCookieJar())
                .build();

        // retrofit默认配置
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                // Gson转换支持
                .addConverterFactory(GsonConverterFactory.create(DefaultGson.getInstance()))
                .build();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);    }
}
