package pers.geolo.guitarworld.presenter.comment;

import java.util.Date;

import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.CommentApi;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.view.AddCommentView;

public class AddCommentPresenter extends BasePresenter<AddCommentView> {

    private int worksId;

    public void setWorksId(int worksId) {
        this.worksId = worksId;
    }

    /**
     * 增加评论
     */
    public  void addComment() {
        String author = CustomContext.getInstance().getLogInfo().getUsername();
        Comment comment = new Comment(worksId, author, new Date(), getView().getComment());
        HttpService.getInstance().getAPI(CommentApi.class)
                .addComment(comment).enqueue(new MvpCallBack<Void>(getView()) {
            @Override
            public void onSuccess(Void responseData) {
                getView().showToast("评论成功！");
                getView().setCommentText("");
                getView().toCommentListView();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                getView().showToast("评论失败");
            }

            @Override
            public void onFailure() {
                getView().showToast("评论失败");
            }
        });
    }
}
