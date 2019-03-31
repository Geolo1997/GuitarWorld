package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;
import pers.geolo.guitarworld.view.list.ListView;

public interface CommentListView extends ListView<CommentItemView>, ToastView, RefreshView {
}
