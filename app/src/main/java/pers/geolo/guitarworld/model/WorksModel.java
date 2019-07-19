package pers.geolo.guitarworld.model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.ProgressRequestBody;
import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.api.WorksApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.callback.DataCallback;

import java.io.File;
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
}
