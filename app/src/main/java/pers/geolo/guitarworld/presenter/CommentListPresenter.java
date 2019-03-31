package pers.geolo.guitarworld.presenter;

import java.util.List;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.CommentApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.CommentListView;

public class CommentListPresenter extends BasePresenter<CommentListView> {

    private int worksId;
    private List<Comment> commentList;

    public void setWorksId(int worksId) {
        this.worksId = worksId;
    }

    /**
     * 加载评论列表
     */
    public void loadingCommentList() {
        getView().showRefreshing();
        HttpService.getInstance().getAPI(CommentApi.class)
                .listCommentOfWorks(worksId)
                .enqueue(new MvpNetworkCallBack<List<Comment>>(getView()) {
                    @Override
                    public void onSuccess(List<Comment> responseData) {
                        commentList = responseData;
                        getView().onBindView(() -> commentList.size(), (view, index) -> {
                            Comment comment = commentList.get(index);
                            view.setAuthor(comment.getAuthor());
                            view.setCreateTime(comment.getCreateTime());
                            view.setContent(comment.getContent());
                        });
                        getView().hideRefreshing();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        getView().showToast("网络错误！");
                        getView().hideRefreshing();
                    }

                    @Override
                    public void onFailure() {
                        getView().showToast("网络错误！");
                        getView().hideRefreshing();
                    }
                });
    }
}
