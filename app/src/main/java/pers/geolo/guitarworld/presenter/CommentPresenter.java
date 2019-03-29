package pers.geolo.guitarworld.presenter;

import java.util.List;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.CommentAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.view.CommentView;

public class CommentPresenter {

    public static void loadingComment(CommentView commentView) {
        HttpService.getInstance().getAPI(CommentAPI.class)
                .listCommentOfWorks(commentView.getWorksId())
                .enqueue(new BaseCallback<List<Comment>>() {
                    @Override
                    public void onSuccess(List<Comment> responseData) {
                        commentView.setDataList(responseData);
                        commentView.loadingCommentOnSuccess();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        commentView.showToast("网络错误！");
                        commentView.hideRefreshing();
                    }

                    @Override
                    public void onFailure() {
                        commentView.showToast("网络错误！");
                        commentView.hideRefreshing();
                    }
                });
    }
}
