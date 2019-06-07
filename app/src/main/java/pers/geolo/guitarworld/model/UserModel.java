package pers.geolo.guitarworld.model;

import java.util.List;

import pers.geolo.guitarworld.constant.HttpConstants;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.http.HttpClient;
import pers.geolo.guitarworld.http.HttpMethod;
import pers.geolo.guitarworld.http.callback.ISuccess;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-07
 */
public class UserModel {

    public static void getAllUser(ISuccess<List<User>> callback) {
        HttpClient.newRequest()
                .url(HttpConstants.ALL_USER)
                .method(HttpMethod.GET)
                .success(callback)
                .execute();
    }

    public static void getFollower(String follower, ISuccess<List<User>> callback) {
        HttpClient.newRequest()
                .url(HttpConstants.BASE_URL + follower + "/follower")
                .method(HttpMethod.GET)
                .success(callback)
                .execute();
    }

    public static void getFollowing(String following, ISuccess<List<User>> callback) {
        HttpClient.newRequest()
                .url(HttpConstants.BASE_URL + following + "/following")
                .method(HttpMethod.GET)
                .success(callback)
                .execute();
    }

    public static void addRelation(UserRelation userRelation, ISuccess<Void> voidISuccess) {
        HttpClient.newRequest()
                .url(HttpConstants.ADD_RELATION)
                .method(HttpMethod.POST)
                .body(userRelation)
                .success(voidISuccess)
                .execute();
    }

    public static void removeRelation(UserRelation userRelation, ISuccess<Void> voidISuccess) {
        HttpClient.newRequest()
                .url(HttpConstants.ADD_RELATION)
                .method(HttpMethod.DELETE)
                .params("follower", userRelation.getFollowerUsername())
                .params("following", userRelation.getFollowingUsername())
                .success(voidISuccess)
                .execute();
    }

    public static void getProfile(String username, ISuccess<User> iSuccess) {
        HttpClient.newRequest()
                .url(HttpConstants.USER)
                .method(HttpMethod.GET)
                .params("username", username)
                .success(iSuccess)
                .execute();
    }
}
