package pers.geolo.guitarworld.view;

public interface LoginView extends BaseView {

    void setSavePassword(boolean savePassword);

    void setAutoLogin(boolean autoLogin);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    void onLoginSuccess();

    void onLoginError();

    void onLoginFailure();
}
