package pers.geolo.guitarworld.ui.temp;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-30
 */
public class CallbackInterfaces {
    public interface ISuccess<T> {
        void onSuccess(T data);
    }

    public interface IFailure<T> {
        void onFailure(T data);
    }
}
