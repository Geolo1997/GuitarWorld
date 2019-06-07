package pers.geolo.guitarworld.http;

import java.util.Map;

import pers.geolo.guitarworld.http.callback.IError;
import pers.geolo.guitarworld.http.callback.IFailure;
import pers.geolo.guitarworld.http.callback.IProgress;
import pers.geolo.guitarworld.http.callback.ISuccess;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public class HttpRequestBuilder {

    private HttpHandler handler;
    private HttpRequest httpRequest;

    HttpRequestBuilder(HttpHandler handler) {
        httpRequest = new HttpRequest();
        this.handler = handler;
    }

    public HttpRequestBuilder url(String url) {
        httpRequest.setUrl(url);
        return this;
    }

    public HttpRequestBuilder method(HttpMethod method) {
        httpRequest.setMethod(method);
        return this;
    }

    public HttpRequestBuilder headers(String key, String value) {
        httpRequest.getHeaders().put(key, value);
        return this;
    }

    public HttpRequestBuilder headers(Map<String, String> headers) {
        httpRequest.getHeaders().putAll(headers);
        return this;
    }

    public HttpRequestBuilder params(String key, Object value) {
        httpRequest.getParams().put(key, value);
        return this;
    }

    public HttpRequestBuilder params(Map<String, Object> params) {
        httpRequest.getParams().putAll(params);
        return this;
    }

    public HttpRequestBuilder body(Object body) {
        httpRequest.setBody(body);
        return this;
    }

    public <T> HttpRequestBuilder success(ISuccess<T> iSuccess) {
        httpRequest.setiSuccess(iSuccess);
        return this;
    }

    public HttpRequestBuilder error(IError iError) {
        httpRequest.setiError(iError);
        return this;
    }

    public HttpRequestBuilder failure(IFailure iFailure) {
        httpRequest.setiFailure(iFailure);
        return this;
    }

    public HttpRequestBuilder progress(IProgress iProgress) {
        httpRequest.setiProgress(iProgress);
        return this;
    }

    private void check() {
        if (httpRequest.getUrl() == null || httpRequest.getUrl().trim().equals("")) {
            throw new RuntimeException("request url can not be null!");
        }
        if (httpRequest.getMethod() == null) {
            throw new RuntimeException("request method can not be null!");
        }
    }

    public void execute() {
        check();
        handler.execute(httpRequest);
    }
}
