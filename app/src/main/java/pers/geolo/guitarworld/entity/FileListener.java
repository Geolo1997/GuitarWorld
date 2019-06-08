package pers.geolo.guitarworld.entity;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public interface FileListener<T> {

    void onProgress(int currentLength, int totalLength);

    void onReturn(T t);

    void onError(String message);
}
