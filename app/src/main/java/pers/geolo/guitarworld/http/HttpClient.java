package pers.geolo.guitarworld.http;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public class HttpClient {

    public static final long DEFAULT_CONNECT_TIME_OUT = 2000;
    public static final long DEFAULT_WRITE_TIME_OUT = 2000;
    public static final long DEFAULT_READ_TIME_OUT = 2000;

    private static HttpConfigBuilder httpConfigBuilder;

    private static void defaultInit() {
        httpConfigBuilder = new HttpConfigBuilder();
        httpConfigBuilder.connectTimeOut(DEFAULT_CONNECT_TIME_OUT)
                .writeTimeOut(DEFAULT_WRITE_TIME_OUT)
                .readTimeOut(DEFAULT_READ_TIME_OUT);
    }

    public static HttpConfigBuilder init() {
        defaultInit();
        return httpConfigBuilder;
    }

    public static HttpRequestBuilder newRequest() {
        return new HttpRequestBuilder(httpConfigBuilder.getHandler());
    }
}
