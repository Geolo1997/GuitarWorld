package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;

public interface WorksDetailView extends WorksItemView, RefreshView, ToastView {

    int getWorksId();
}
