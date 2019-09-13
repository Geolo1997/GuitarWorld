package pers.geolo.guitarworld.model;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.ProgressRequestBody;
import pers.geolo.guitarworld.network.api.UserApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;

import java.io.File;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class UserModel {

    UserApi userApi = HttpClient.getService(UserApi.class);

    public void getAllUser(DataCallback<List<User>> listener) {
        userApi.getAllUsers().enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }

    public void getPublicProfile(String username, DataCallback<User> listener) {
        userApi.getPublicProfile(username).enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }

    public void updateMyProfile(User user, DataCallback<Void> listener) {
        userApi.updateMyProfile(user).enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }


    public void getFollower(String username, DataCallback<List<User>> listener) {
        userApi.getFollower(username).enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }

    public void getFollowing(String username, DataCallback<List<User>> listener) {
        userApi.getFollowing(username).enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }

    public void addRelation(UserRelation userRelation, DataCallback<Void> voidDataCallback) {
    }

    public void removeRelation(UserRelation userRelation, DataCallback<Void> voidDataCallback) {
    }

    public void updateAvatar(File file, FileListener<String> listener) {
        RequestBody requestBody = new ProgressRequestBody(file, "multipart/form-data", 1024, listener);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
        userApi.updateAvatar(part).enqueue(new BaseCallback<String>() {
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
