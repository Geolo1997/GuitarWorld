package pers.geolo.guitarworld.view;

import java.util.List;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;

public interface WorksListView extends RefreshView, ToastView {

    void setDataList(List<Works> responseData);

}
