package pers.geolo.guitarworld.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.model.listener.GetImageListener;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.callback.DataCallback;
import pers.geolo.guitarworld.network.callback.FileCallBack;
import pers.geolo.guitarworld.util.FileUtils;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class ImageModel {

    public static FileApi fileApi = HttpClient.getService(FileApi.class);
    private static Map<String, Bitmap> imageBitmapHolder = new HashMap<>();

    public static void getImage(String imagePath, FileListener<Bitmap> listener) {
        fileApi.getImage(imagePath).enqueue(new FileCallBack() {
            @Override
            protected void onResponseInputStream(InputStream inputStream) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                new Handler(Looper.getMainLooper()).post(() -> {
                    listener.onReturn(bitmap);
                });
            }

            @Override
            protected void onError(int code, String message) {
                listener.onError(message);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onError("网络错误");
            }
        });
    }

    public static void getAvatar(String username, FileListener<Bitmap> listener) {
        fileApi.getAvatar(username).enqueue(new FileCallBack() {
            @Override
            protected void onResponseInputStream(InputStream inputStream) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                new Handler(Looper.getMainLooper()).post(() -> {
                    listener.onReturn(bitmap);
                });
            }

            @Override
            protected void onError(int code, String message) {
                listener.onError(message);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onError("网络错误");
            }
        });
    }

    public static void upload(File file, DataListener<String> listener) {
        MultipartBody.Part body = FileUtils.createMultipartBodyPart(file, "image");
        fileApi.uploadImage(body).enqueue(new DataCallback<String>(listener) {
            @Override
            public void onSuccess(String responseData) {
                listener.onReturn(responseData);
            }
        });
    }
}
