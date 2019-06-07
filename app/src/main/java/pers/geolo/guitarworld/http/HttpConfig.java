package pers.geolo.guitarworld.http;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public class HttpConfig {

    private long connectTimeOut;
    private long writeTimeOut;
    private long readTimeOut;

    public long getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(long connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public long getWriteTimeOut() {
        return writeTimeOut;
    }

    public void setWriteTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public long getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
    }
}
