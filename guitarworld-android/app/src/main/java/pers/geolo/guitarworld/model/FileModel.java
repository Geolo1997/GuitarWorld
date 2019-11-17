package pers.geolo.guitarworld.model;

import android.graphics.Bitmap;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.network.HttpClient;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/16
 */
public class FileModel {

    public String getFileUrl(String filePath) {
        return HttpClient.baseUrl + "file?filePath=" + filePath;
    }

    public void loadImage(String filePath, FileListener<Bitmap> listener) {

    }
}
