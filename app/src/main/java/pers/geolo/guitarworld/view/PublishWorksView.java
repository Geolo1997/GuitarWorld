package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.base.ToastView;

public interface PublishWorksView extends ToastView {

    String getWorksTitle();

    Object getContent();

    void toMainView();
}
