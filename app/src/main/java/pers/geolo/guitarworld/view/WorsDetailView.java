package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.view.base.RefreshView;

public interface WorsDetailView extends WorksItemView, RefreshView {

    void loadingWorksDetailOnSuccess();

    int getWorksId();
}
