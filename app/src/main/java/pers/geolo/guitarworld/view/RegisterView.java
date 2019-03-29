package pers.geolo.guitarworld.view;

public interface RegisterView extends BaseView {
    void onRegisterSuccess();

    void onRegisterError();

    void onRegisterFailure();

    String getUsername();

    String getPassword();

    String getEmail();
}
