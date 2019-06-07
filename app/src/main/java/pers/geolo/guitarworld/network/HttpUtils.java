package pers.geolo.guitarworld.network;

import java.util.List;
import java.util.Map;
import retrofit2.Callback;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.User;
import pers.geolo.guitarworld.entity.UserRelation;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.network.api.CommentRequest;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.callback.Response;
import pers.geolo.guitarworld.network.request.UserRelationRequest;
import pers.geolo.guitarworld.network.request.UserRequest;
import pers.geolo.guitarworld.network.request.WorksRequest;

/**
 * @author 桀骜(Geolo)
 * @date 2019-05-31
 */
public class HttpUtils {
    private static UserRequest userRequest = Network.getService(UserRequest.class);
    private static WorksRequest worksRequest = Network.getService(WorksRequest.class);
    private static CommentRequest commentRequest = Network.getService(CommentRequest.class);
    private static UserRelationRequest userRelationRequest = Network.getService(UserRelationRequest.class);

    public static void getWorks(Map<String, Object> filter, Callback<ResponseBody<List<Works>>> callback) {
        worksRequest.getWorks(filter).enqueue(callback);
    }

    public static void getAllUser(BaseCallback<List<User>> callback) {
        userRequest.getAllUsers().enqueue(callback);
    }

    public static void getFollowing(String username, BaseCallback<List<User>> callback) {
        userRequest.getFollowing(username).enqueue(callback);
    }

    public static void getFollower(String username, BaseCallback<List<User>> callback) {
        userRequest.getFollower(username).enqueue(callback);
    }

    public static void removeWorks(Map<String, Object> filter, Callback<ResponseBody<Void>> callback) {
        worksRequest.removeWorks(filter).enqueue(callback);
    }

    public static void addRelation(UserRelation userRelation, Callback<ResponseBody<Void>> callback) {
        userRelationRequest.addRelation(userRelation).enqueue(callback);
    }

    public static void removeRelation(UserRelation userRelation, Callback<ResponseBody<Void>> callback) {
        userRelationRequest.removeRelation(userRelation).enqueue(callback);
    }

    public static String getAavatarRooter(String username) {
        return Network.baseUrl + "avatar?username=" + username;
    }

    public static String getImageRooter(String relativeRooter) {
        return Network.baseUrl + "image?imagePath=" + relativeRooter;
    }

    public static void getCommentList(Map<String, Object> filter, Response<List<Comment>> response) {
        commentRequest.getComments(filter).enqueue(response);
    }

    public static void addComment(Comment comment, Response<Void> voidResponse) {
        commentRequest.addComment(comment).enqueue(voidResponse);
    }

    public static void publishWorks(Works works, Response<Void> response) {
        worksRequest.publishWorks(works).enqueue(response);
    }
}