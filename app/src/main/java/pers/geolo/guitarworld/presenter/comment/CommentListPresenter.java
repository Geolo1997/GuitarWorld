package pers.geolo.guitarworld.presenter.comment;

import java.util.HashMap;
import java.util.List;

import pers.geolo.guitarworld.ui.base.BaseListPresenter;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.CommentApi;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.view.CommentItemView;
import pers.geolo.guitarworld.view.CommentListView;

public class CommentListPresenter extends BaseListPresenter<CommentListView, CommentItemView, Comment> {

    /**
     * 加载评论列表
     */
    public void loadCommentList() {
        getView().showRefreshing();
        HttpService.getInstance().getAPI(CommentApi.class)
                .getComments(getFilter())
                .enqueue(new MvpCallBack<List<Comment>>(getView()) {
                    @Override
                    public void onSuccess(List<Comment> responseData) {
                        addAllItemView(responseData);
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

    public void removeComment(int index) {
        HashMap<String, Object> filter = new HashMap<>();
        filter.put("id", getDataList().get(index).getId());
        HttpService.getInstance().getAPI(CommentApi.class)
                .removeComment(filter)
                .enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        getView().removeItemView(index);
                        getView().showToast("删除成功");
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        getView().showToast("删除失败");
                    }

                    @Override
                    public void onFailure() {
                        getView().showToast("网络错误");
                    }
                });
    }

    public void showItemOptions(int index) {
        // 作者权限选项
        String[] authorOptions = new String[]{"删除"};
        // 浏览者权限选项
        String[] viewerOptions = new String[]{};

        String username = getItemView(index).getAuthor();
        String currentUsername = CustomContext.getInstance().getLogInfo().getUsername();

        String[] options;
        if (currentUsername.equals(username)) {
            options = authorOptions;
        } else {
            options = viewerOptions;
        }
        getItemView(index).showOptions(options);
    }

    @Override
    public void onBindItemView(CommentItemView itemView, int index) {
        Comment comment = getDataList().get(index);
        itemView.setAuthor(comment.getAuthor());
        itemView.setCreateTime(comment.getCreateTime());
        itemView.setContent(comment.getContent());
    }
}
