package pers.geolo.guitarworld.model;

import java.util.List;

import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.UserApi;
import pers.geolo.guitarworld.network.callback.DataCallback;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class UserModel {

    private static UserApi userApi = HttpClient.getService(UserApi.class);

    public static void getAllUser(DataListener<List<User>> listener) {
        userApi.getAllUsers().enqueue(new DataCallback<List<User>>(listener) {
            @Override
            public void onSuccess(List<User> users) {
                listener.onReturn(users);
            }
        });
    }

    public static void getPublicProfile(String username, DataListener<User> listener) {
        userApi.getPublicProfile(username).enqueue(new DataCallback<User>(listener) {
            @Override
            public void onSuccess(User user) {
                listener.onReturn(user);
            }
        });
    }

    public static void updateMyProfile(User user, DataListener<Void> listener) {
        userApi.updateMyProfile(user).enqueue(new DataCallback<Void>(listener) {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onReturn(aVoid);
            }
        });
    }


    public static void getFollower(String username, DataListener<List<User>> listener) {
        userApi.getFollower(username).enqueue(new DataCallback<List<User>>(listener) {
            @Override
            public void onSuccess(List<User> users) {
                listener.onReturn(users);
            }
        });
    }

    public static void getFollowing(String username, DataListener<List<User>> listener) {
        userApi.getFollowing(username).enqueue(new DataCallback<List<User>>(listener) {
            @Override
            public void onSuccess(List<User> users) {
                listener.onReturn(users);
            }
        });
    }

    public static void addRelation(UserRelation userRelation, DataListener<Void> voidDataListener) {
    }

    public static void removeRelation(UserRelation userRelation, DataListener<Void> voidDataListener) {
    }
}
