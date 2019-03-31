package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.base.ToastView;

public interface AddCommentView extends ToastView {

    String getComment();

    void setCommentText(String s);

    void toCommentListView();
}
