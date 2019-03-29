package pers.geolo.guitarworld.view;

import java.util.List;

import pers.geolo.guitarworld.entity.Comment;
import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;

public interface CommentView extends ToastView, RefreshView {

    void setDataList(List<Comment> responseData);

    void loadingCommentOnSuccess();

    int getWorksId();
}
