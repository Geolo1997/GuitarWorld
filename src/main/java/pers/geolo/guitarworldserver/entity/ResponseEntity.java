package pers.geolo.guitarworldserver.entity;

//@Component
public class ResponseEntity<T> {

    private int code;
    private T data;
    private String message;

    public ResponseEntity() {
    }

    public ResponseEntity(int code) {
        this.code = code;
    }

    public ResponseEntity(int code, T data, String message) {
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
        return "ResponseEntity{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
