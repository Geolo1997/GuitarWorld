package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.base.BaseView;

public interface RegisterView extends BaseView {

    String getUsername();

    String getPassword();

    String getEmail();

    void toMainView();

    void showHint(String errorMessage);
}
