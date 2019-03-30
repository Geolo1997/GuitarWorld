package pers.geolo.guitarworld.view;

import pers.geolo.guitarworld.view.base.LoadingView;

public interface LoginView extends LoadingView {

    void setSavePassword(boolean savePassword);

    void setAutoLogin(boolean autoLogin);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    void toMainView();

    void showHint(String errorMessage);
}
