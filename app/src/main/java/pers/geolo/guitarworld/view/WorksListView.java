package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;
import pers.geolo.guitarworld.view.list.ListView;

public interface WorksListView extends ListView<WorksListItemView>, RefreshView, ToastView {

    void toWorksDetailView(int worksId);
}
