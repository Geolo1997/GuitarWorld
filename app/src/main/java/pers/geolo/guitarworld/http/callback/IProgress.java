package pers.geolo.guitarworld.http.callback;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-06
 */
public interface IProgress {
    void onProgress(long currentLength, long totalLength);
}
