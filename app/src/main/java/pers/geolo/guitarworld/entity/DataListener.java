package pers.geolo.guitarworld.entity;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public interface DataListener<T> {

    void onReturn(T t);

    void onError(String message);
}
