package pers.geolo.guitarworld.model;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.FileListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.ProgressRequestBody;
import pers.geolo.guitarworld.network.api.FileApi;
import pers.geolo.guitarworld.network.api.UserApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.callback.DataCallback;
import retrofit2.http.Multipart;

import java.io.File;
import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class UserModel {

    UserApi userApi = HttpClient.getService(UserApi.class);

    public void getAllUser(DataListener<List<User>> listener) {
        userApi.getAllUsers().enqueue(new DataCallback<List<User>>(listener) {
            @Override
            public void onSuccess(List<User> users) {
                listener.onReturn(users);
            }
        });
    }

    public void getPublicProfile(String username, DataListener<User> listener) {
        userApi.getPublicProfile(username).enqueue(new DataCallback<User>(listener) {
            @Override
            public void onSuccess(User user) {
                listener.onReturn(user);
            }
        });
    }

    public void updateMyProfile(User user, DataListener<Void> listener) {
        userApi.updateMyProfile(user).enqueue(new DataCallback<Void>(listener) {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onReturn(aVoid);
            }
        });
    }


    public void getFollower(String username, DataListener<List<User>> listener) {
        userApi.getFollower(username).enqueue(new DataCallback<List<User>>(listener) {
            @Override
            public void onSuccess(List<User> users) {
                listener.onReturn(users);
            }
        });
    }

    public void getFollowing(String username, DataListener<List<User>> listener) {
        userApi.getFollowing(username).enqueue(new DataCallback<List<User>>(listener) {
            @Override
            public void onSuccess(List<User> users) {
                listener.onReturn(users);
            }
        });
    }

    public void addRelation(UserRelation userRelation, DataListener<Void> voidDataListener) {
    }

    public void removeRelation(UserRelation userRelation, DataListener<Void> voidDataListener) {
    }

    public void updateAvatar(File file, FileListener<String> listener) {
        RequestBody requestBody = new ProgressRequestBody(file, "multipart/form-data", 1024, listener);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
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
