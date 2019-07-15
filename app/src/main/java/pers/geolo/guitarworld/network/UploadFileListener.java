package pers.geolo.guitarworld.network;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/15
 */
public interface UploadFileListener {

    void onProgress(long currentLength, long totalLength);

    void onFinish();

    void onError(String message);
}
