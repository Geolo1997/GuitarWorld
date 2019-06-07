package pers.geolo.guitarworld.network;

import java.io.File;
import okhttp3.MultipartBody;

import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.ui.temp.CallbackInterfaces;
import pers.geolo.guitarworld.util.FileUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-30
 */
public class ImageUploader {
    public static FileApi fileApi = Network.getService(FileApi.class);

    public static void upload(File file, CallbackInterfaces.ISuccess<String> iSuccess,
                              CallbackInterfaces.IFailure<String> iFailure) {
        MultipartBody.Part body = FileUtils.createMultipartBodyPart(file, "image");
        fileApi.uploadImage(body).enqueue(new BaseCallback<String>() {
            @Override
            public void onSuccess(String responseData) {
                iSuccess.onSuccess(responseData);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                if (iFailure != null) {
                    iFailure.onFailure(errorMessage);
                }
            }

            @Override
            public void onFailure() {
                if (iFailure != null) {
                    iFailure.onFailure("网络错误");
                }
            }
        });
    }
}
