package pers.geolo.guitarworld.http;

import java.util.HashMap;
import java.util.Map;

import pers.geolo.guitarworld.http.callback.IError;
import pers.geolo.guitarworld.http.callback.IFailure;
import pers.geolo.guitarworld.http.callback.IProgress;
import pers.geolo.guitarworld.http.callback.ISuccess;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public class HttpRequest {

    private String url;
    private HttpMethod method;
    private Map<String, String> headers;
    private Map<String, Object> params;
    private Object body;
    private ISuccess iSuccess;
    private IError iError;
    private IFailure iFailure;
    private IProgress iProgress;

    public HttpRequest() {
        headers = new HashMap<>();
        params = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public <T> ISuccess<T> getiSuccess() {
        return iSuccess;
    }

    public <T> void setiSuccess(ISuccess<T> iSuccess) {
        this.iSuccess = iSuccess;
    }

    public IError getiError() {
        return iError;
    }

    public void setiError(IError iError) {
        this.iError = iError;
    }

    public IFailure getiFailure() {
        return iFailure;
    }

    public void setiFailure(IFailure iFailure) {
        this.iFailure = iFailure;
    }

    public IProgress getiProgress() {
        return iProgress;
    }

    public void setiProgress(IProgress iProgress) {
        this.iProgress = iProgress;
    }
}
