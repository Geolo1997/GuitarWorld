package pers.geolo.guitarworld.presenter;

import java.util.Date;
import java.util.List;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.CommentApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.AddCommentView;
import pers.geolo.guitarworld.view.CommentListView;

public class CommentPresenter {

    /**
     * 增加评论
     * 获取评论文本
     *
     * @param addCommentView
     */
    public static void addComment(AddCommentView addCommentView, CommentListView commentListView) {
        String author = DAOService.getInstance().getCurrentLogInfo().getUsername();
        Comment comment = new Comment(addCommentView.getWorksId(), author, new Date(), addCommentView.getComment());
        HttpService.getInstance().getAPI(CommentApi.class)
                .addComment(comment).enqueue(new MvpNetworkCallBack<Void>(addCommentView) {
            @Override
            public void onSuccess(Void responseData) {
                addCommentView.showToast("评论成功！");
                addCommentView.setCommentText("");
                addCommentView.toCommentListView();
                loadingCommentList(commentListView);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                addCommentView.showToast("评论失败");
            }

            @Override
            public void onFailure() {
                addCommentView.showToast("评论失败");
            }
        });
    }

    /**
     * 加载评论列表
     *
     * @param commentListView 评论列表视图
     */
    public static void loadingCommentList(CommentListView commentListView) {
        commentListView.showRefreshing();
        HttpService.getInstance().getAPI(CommentApi.class)
                .listCommentOfWorks(commentListView.getWorksId())
                .enqueue(new MvpNetworkCallBack<List<Comment>>(commentListView) {
                    @Override
                    public void onSuccess(List<Comment> responseData) {
                        commentListView.setDataList(responseData);
                        commentListView.hideRefreshing();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        commentListView.showToast("网络错误！");
                        commentListView.hideRefreshing();
                    }

                    @Override
                    public void onFailure() {
                        commentListView.showToast("网络错误！");
                        commentListView.hideRefreshing();
                    }
                });
    }
}
