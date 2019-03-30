package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.entity.Works;
import pers.geolo.guitarworld.view.base.BaseView;
import pers.geolo.guitarworld.view.base.ToastView;

public interface WorksItemOptionView extends ToastView {
    Works getWorks();

    void showOptions(String[] options);

    void removeWorks();
}
