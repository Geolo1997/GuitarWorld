package pers.geolo.guitarworld.model;

import android.graphics.Bitmap;

import pers.geolo.guitarworld.model.listener.GetImageListener;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class ImageModel {

    public static void getImage(String imagePath, GetImageListener listener) {
        //TODO 通过网络获取图片
        Bitmap bitmap = null;
        listener.onSuccess(bitmap);
    }

    public static void getAvatar(String string, GetImageListener getImageListener) {
        // TODO

    }
}
