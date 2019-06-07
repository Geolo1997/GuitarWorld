package pers.geolo.guitarworld.http;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public class HttpConfigBuilder {

    private HttpHandler handler;
    private HttpConfig httpConfig;

    HttpConfigBuilder() {
        httpConfig = new HttpConfig();
    }

    public HttpConfigBuilder handler(HttpHandler handler) {
        this.handler = handler;
        return this;
    }

    public HttpConfigBuilder connectTimeOut(long connectTimeOut) {
        httpConfig.setConnectTimeOut(connectTimeOut);
        return this;
    }

    public HttpConfigBuilder writeTimeOut(long writeTimeOut) {
        httpConfig.setWriteTimeOut(writeTimeOut);
        return this;
    }

    public HttpConfigBuilder readTimeOut(long readTimeOut) {
        httpConfig.setReadTimeOut(readTimeOut);
        return this;
    }

    public HttpConfig getHttpConfig() {
        return httpConfig;
    }

    public HttpHandler getHandler() {
        if (handler == null) {
            throw new RuntimeException("handler can not be null!");
        }
        return handler;
    }

    public void configure() {
        if (handler == null) {
            throw new RuntimeException("handler can not be null!");
        }
        handler.init(httpConfig);
    }
}
