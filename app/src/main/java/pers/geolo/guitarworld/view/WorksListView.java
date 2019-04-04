package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.list.ListView;

public interface WorksListView extends ListView<WorksListItemView> {

    void toWorksDetailView(int worksId);
}
