package pers.geolo.guitarworld.model.listener;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public interface RegisterListener {
    void onSuccess();

    void onError(String message);
}
