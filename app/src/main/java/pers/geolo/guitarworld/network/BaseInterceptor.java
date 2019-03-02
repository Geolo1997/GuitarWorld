package pers.geolo.guitarworld.network;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        System.out.println(chain.request().toString());
        return chain.proceed(chain.request());
    }
}
