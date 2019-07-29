package pers.geolo.guitarworld.model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.ParamBeanHandler;
import pers.geolo.guitarworld.network.ProgressRequestBody;
import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.callback.DataCallback;
import pers.geolo.guitarworld.network.param.WorksParam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public class WorksModel {

    private WorksApi worksApi = HttpClient.getService(WorksApi.class);
    private FileApi fileApi = HttpClient.getService(FileApi.class);

    public void publishWorks(Works works, DataListener<Void> listener) {
        worksApi.publishWorks(works).enqueue(new DataCallback<>(listener));
    }

    public void getWorks(Map<String, Object> filter, DataListener<List<Works>> listener) {
        worksApi.getWorks(filter).enqueue(new DataCallback<>(listener));
    }

    public void getWorks(int worksId, DataListener<Works> listener) {
        WorksParam param = new WorksParam();
        param.setWorksId(worksId);
        worksApi.getWorks(ParamBeanHandler.handle(param))
                .enqueue(new BaseCallback<List<Works>>() {
                    @Override
                    public void onSuccess(List<Works> works) {
                        if (works != null && works.size() > 0) {
                            listener.onReturn(works.get(0));
                        } else {
                            listener.onReturn(null);
                        }
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        listener.onError(errorMessage);
                    }

                    @Override
                    public void onFailure() {
                        listener.onError("网络错误");
                    }
                });
    }

    public void deleteWorks(Map<String, Object> filter, DataListener<Void> listener) {
        worksApi.deleteWorks(filter).enqueue(new DataCallback<>(listener));
    }

    public void publishVideoWorks(File video, FileListener<String> listener) {

        RequestBody requestBody = new ProgressRequestBody(video, "multipart/form-data", 1024, listener);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", video.getName(), requestBody);
        // TODO 改为文件上传接口
        worksApi.uploadImage(part).enqueue(new BaseCallback<String>() {
            @Override
            public void onSuccess(String s) {
                listener.onFinish(s);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                listener.onError(errorMessage);
            }

            @Override
            public void onFailure() {
                listener.onError("网络错误");
            }
        });
    }

    public void uploadImageUrls(List<File> imageFiles, FileListener<List<String>> listener) {
        List<String> imageUrls = new ArrayList<>(imageFiles.size());
        final Boolean[] hasError = {false};
        for (int i = 0; i < imageFiles.size(); i++) {
            File image = imageFiles.get(i);
            int finalI = i;
            FileListener<String> imageListener = new FileListener<String>() {
                @Override
                public void onProgress(long currentLength, long totalLength) {

                }

                @Override
                public void onFinish(String s) {
                    listener.onProgress(finalI, imageFiles.size());
                    imageUrls.add(s);
                    // 图片全部上传完成
                    if (imageUrls.size() == imageFiles.size()) {
                        listener.onFinish(imageUrls);
                    }
                }

                @Override
                public void onError(String message) {
                    if (!hasError[0]) {
                        listener.onError(message);
                        hasError[0] = false;
                    }
                }
            };
            RequestBody requestBody = new ProgressRequestBody(image, "multipart/form-data",
                    1024, imageListener);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", image.getName(), requestBody);
            worksApi.uploadImage(part).enqueue(new BaseCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    imageListener.onFinish(s);
                }

                @Override
                public void onError(int errorCode, String errorMessage) {
                    imageListener.onError(errorMessage);
                }

                @Override
                public void onFailure() {
                    imageListener.onError("网络错误");
                }
            });
        }
    }
}
