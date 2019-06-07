package pers.geolo.guitarworld.http.retrofit;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import pers.geolo.guitarworld.http.HttpConfig;
import pers.geolo.guitarworld.http.HttpHandler;
import pers.geolo.guitarworld.http.HttpRequest;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public class RetrofitHandler implements HttpHandler {

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private RetrofitService retrofitService;

    @Override
    public void init(HttpConfig httpConfigure) {
        okHttpClient = new OkHttpClient.Builder()
                // 连接 超时时间
                .connectTimeout(httpConfigure.getConnectTimeOut(), TimeUnit.MILLISECONDS)
                // 写操作 超时时间
                .writeTimeout(httpConfigure.getWriteTimeOut(), TimeUnit.MILLISECONDS)
                // 读操作 超时时间
                .readTimeout(httpConfigure.getReadTimeOut(), TimeUnit.MILLISECONDS)
                // 错误重连
                .retryOnConnectionFailure(true)
                // 开启cookie
//                .cookieJar(new DefaultCookieJar())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://lacalhost:8080")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    @Override
    public void execute(HttpRequest httpRequest) {
        Call call = null;
        switch (httpRequest.getMethod()) {
            case GET:
                call = retrofitService.get(httpRequest.getUrl(), httpRequest.getHeaders(), httpRequest.getParams());
                break;
            case POST:
                if (httpRequest.getBody() == null) {
                    call = retrofitService.post(httpRequest.getUrl(), httpRequest.getHeaders(),
                            httpRequest.getParams());
                } else {
                    if (httpRequest.getParams() != null) {
                        throw new RuntimeException("params should be null!");
                    }
                    call = retrofitService.postRaw(httpRequest.getUrl(), httpRequest.getHeaders(),
                            createJsonBody(httpRequest.getBody()));
                }
                break;
            case PUT:
                if (httpRequest.getBody() == null) {
                    call = retrofitService.put(httpRequest.getUrl(), httpRequest.getHeaders(), httpRequest.getParams());
                } else {
                    if (httpRequest.getParams() != null) {
                        throw new RuntimeException("params should be null!");
                    }
                    call = retrofitService.putRaw(httpRequest.getUrl(), httpRequest.getHeaders(),
                            createJsonBody(httpRequest.getBody()));
                }
                break;
            case DELETE:
                call = retrofitService.delete(httpRequest.getUrl(), httpRequest.getHeaders(), httpRequest.getParams());
                break;
            case UPLOAD:
//                retrofitService.upload(httpRequest.getUrl(),httpRequest.getHeaders(),createMultipartBody())
                break;
            case DOWNLOAD:
                break;
        }
        if (call != null) {
            call.enqueue(new RetrofitCallback(httpRequest));
        }
    }

    private RequestBody createJsonBody(Object body) {
        String stringBody = JSON.toJSONString(body);
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), stringBody);
    }

    private MultipartBody.Part createMultipartBody(File file) {
        RequestBody requestBody =
                RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), file);
        return MultipartBody.Part.createFormData("file", file.getName(), requestBody);
    }
}
