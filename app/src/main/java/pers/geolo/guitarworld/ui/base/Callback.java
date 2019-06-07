package pers.geolo.guitarworld.ui.base;

import android.content.Intent;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-03
 */
public interface Callback {

    /**
     * 请求成功
     *
     * @param intent 回调intent
     */
    void onSuccess(Intent intent);

    /**
     * 请求失败
     *
     * @param intent 回调intent
     */
    void onFailure(Intent intent);
}
