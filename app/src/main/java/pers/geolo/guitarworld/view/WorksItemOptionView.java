package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.entity.Works;

public interface WorksItemOptionView extends BaseView {
    Works getWorks();

    void showOptions(String[] options);

    void removeWorks();
}
