package pers.geolo.guitarworld.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.DataListener;
import pers.geolo.guitarworld.network.HttpClient;
import pers.geolo.guitarworld.network.api.CommentApi;
import pers.geolo.guitarworld.network.callback.DataCallback;

/**
 * @author 桀骜(Geolo)
 * @date 2019-06-08
 */
public class CommentModel {

    private static CommentApi commentApi = HttpClient.getService(CommentApi.class);

    public static void getCommentList(HashMap<String, Object> filter, DataListener<List<Comment>> listener) {
        commentApi.getComments(filter).enqueue(new DataCallback<List<Comment>>(listener) {
            @Override
            public void onSuccess(List<Comment> comments) {
                listener.onReturn(comments);
            }
        });
    }

    public static void addComment(Comment comment, DataListener<Void> listener) {
        commentApi.addComment(comment).enqueue(new DataCallback<Void>(listener) {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onReturn(aVoid);
            }
        });
    }

    public static void deleteCommentList(Map<String, Object> filter, DataListener<Void> listener) {
        commentApi.deleteComment(filter).enqueue(new DataCallback<Void>(listener) {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onReturn(aVoid);
            }
        });
    }
}
