package pers.geolo.guitarworld.presenter;

import android.util.Log;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.UserAPI;
import pers.geolo.guitarworld.network.callback.BaseCallback;
import pers.geolo.guitarworld.view.AutoLoginView;
import pers.geolo.guitarworld.view.LoginView;
import pers.geolo.guitarworld.view.LogoutView;
import pers.geolo.guitarworld.view.RegisterView;
import pers.geolo.util.SingletonHolder;

public class AuthPresenter extends BasePresenter {

    public static void register(RegisterView registerView) {

        LogInfo logInfo = new LogInfo(registerView.getUsername(), registerView.getPassword(), false, false);
        HttpService.getInstance().getAPI(UserAPI.class)
                .register(registerView.getUsername(), registerView.getPassword(), registerView.getEmail())
                .enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        DAOService.getInstance().saveLogInfo(logInfo);
                        registerView.onRegisterSuccess();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        registerView.onRegisterError();
                    }

                    @Override
                    public void onFailure() {
                        registerView.onRegisterFailure();
                    }
                });
    }

    public static void loadingLogInfo(LoginView loginView) {
        LogInfo logInfo = DAOService.getInstance().getCurrentLogInfo();
        if (logInfo != null) {
            loginView.setUsername(logInfo.getUsername());
            loginView.setPassword(logInfo.getPassword());
            loginView.setSavePassword(logInfo.isSavePassword());
            loginView.setAutoLogin(logInfo.isAutoLogin());
        }
    }

    public static void login(LoginView loginView) {
        loginView.showLoading();
        HttpService.getInstance()
                .getAPI(UserAPI.class)
                .login(loginView.getUsername(), loginView.getPassword())
                .enqueue(new BaseCallback<Void>() {
                    @Override
                    public void onSuccess(Void responseData) {
                        loginView.hideLoading();
                        loginView.onLoginSuccess();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        Log.d(TAG, "登录错误，错误码：" + errorCode + "错误信息：" + errorMessage);
                        loginView.onLoginError();
                    }

                    @Override
                    public void onFailure() {
                        loginView.onLoginFailure();
                    }
                });
    }

    public static void autoLogin(AutoLoginView autoLoginView) {
        LogInfo logInfo = SingletonHolder.getInstance(DAOService.class).getCurrentLogInfo();
        if (logInfo != null && logInfo.isAutoLogin()) {
            HttpService.getInstance().getAPI(UserAPI.class)
                    .login(logInfo.getUsername(), logInfo.getPassword()).enqueue(new BaseCallback<Void>() {
                @Override
                public void onSuccess(Void responseData) {
                    autoLoginView.onAutoLoginSuccess();
                }

                @Override
                public void onError(int errorCode, String errorMessage) {
                    autoLoginView.onAutoLoginFailure();
                }

                @Override
                public void onFailure() {
                    autoLoginView.onAutoLoginFailure();
                }
            });
        }
    }

    public static void logout(LogoutView logoutView) {
        HttpService.getInstance().getAPI(UserAPI.class)
                .logout().enqueue(new BaseCallback<Void>() {
            @Override
            public void onSuccess(Void responseData) {
                if (logoutView != null) {
                    logoutView.onLogoutSuccess();
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                logoutView.onLogoutError();
            }

            @Override
            public void onFailure() {
                logoutView.onLogoutFailure();
            }
        });
    }
}
