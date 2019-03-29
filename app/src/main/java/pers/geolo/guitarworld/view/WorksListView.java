package pers.geolo.guitarworld.view;

import java.util.List;

import pers.geolo.guitarworld.entity.Works;

public interface WorksListView {

    void showRefreshing();

    void setDataList(List<Works> responseData);

    void hideRefreshing();
}
