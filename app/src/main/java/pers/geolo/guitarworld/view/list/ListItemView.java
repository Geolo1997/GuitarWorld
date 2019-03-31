package pers.geolo.guitarworld.view.list;

import pers.geolo.guitarworld.view.base.ToastView;

public interface ListItemView extends ToastView {

    int getIndex();

    void remove();
}
