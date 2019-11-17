package pers.geolo.guitarworld.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.DataCallback;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.CommentApi;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public class CommentModel {

    private static CommentApi commentApi = HttpClient.getService(CommentApi.class);

    public static void getCommentList(HashMap<String, Object> filter, DataCallback<List<Comment>> listener) {
        commentApi.getComments(filter).enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }

    public static void addComment(Comment comment, DataCallback<Void> listener) {
        commentApi.addComment(comment).enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }

    public static void deleteCommentList(Map<String, Object> filter, DataCallback<Void> listener) {
        commentApi.deleteComment(filter).enqueue(new pers.geolo.guitarworld.network.callback.DataCallback(listener));
    }
}
