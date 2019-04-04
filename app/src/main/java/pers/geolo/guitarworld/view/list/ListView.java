package pers.geolo.guitarworld.view.list;

import pers.geolo.guitarworld.view.base.RefreshView;
import pers.geolo.guitarworld.view.base.ToastView;

public interface ListView<I> extends RefreshView, ToastView {

    int getListSize();

    void addAllItemView();

    void addItemView();

    void addItemView(int index);

    void removeItemView(int index);

    void clearItemView();

    I getItemView(int index);
}
