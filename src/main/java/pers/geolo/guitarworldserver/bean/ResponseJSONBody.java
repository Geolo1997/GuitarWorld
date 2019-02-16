package pers.geolo.guitarworldserver.bean;

import org.springframework.stereotype.Component;

//@Component
public class ResponseJSONBody<T> {

    private int code;
    private T data;
    private String message;

    public ResponseJSONBody() {
    }

    public ResponseJSONBody(int code) {
        this.code = code;
    }

    public ResponseJSONBody(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseJSONBody{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
