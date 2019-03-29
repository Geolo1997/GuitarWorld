package pers.geolo.guitarworld.view;

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showToast(String message);

    void showLongToast(String message);
}
