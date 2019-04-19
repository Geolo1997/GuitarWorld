package pers.geolo.guitarworld;

import pers.geolo.android.http.HttpClient;
import pers.geolo.guitarworld.network.BaseInterceptor;

public class Config {

    private static String baseUrl = "http://192.168.3.13:8080/GuitarWorld/";
    private static String hotPointBaseUrl = "http://192.168.43.191:8080/GuitarWorld/";
    private static HttpClient httpClient;

    // init HttpClient
    static {
        httpClient = new HttpClient.Builder()
                .baseUrl(baseUrl)
                .connectTimeOut(2000)
                .writeTimeOut(10000)
                .readTimeOut(10000)
                .addInterceptor(new BaseInterceptor())
                .build();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return httpClient.getService(serviceClass);
    }
}
