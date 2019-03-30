package pers.geolo.guitarworld.presenter;

import android.util.Log;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.AuthApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.AutoLoginView;
import pers.geolo.guitarworld.view.LoginView;
import pers.geolo.guitarworld.view.LogoutView;
import pers.geolo.guitarworld.view.RegisterView;
import pers.geolo.util.SingletonHolder;

public class AuthPresenter extends BasePresenter {

    /**
     * 注册
     *
     * @param registerView 注册视图
     */
    public static void register(RegisterView registerView) {
        // 从视图中获取注册信息
        String username = registerView.getUsername();
        String password = registerView.getPassword();
        String email = registerView.getEmail();
        // 检查登录信息合法性
        if (username == null || password == null || email == null) {
            registerView.showHint("请填写完整！");
            return;
        } // TODO 检查用户名密码和邮箱字符串规则

        // 构建登录信息
        LogInfo logInfo = new LogInfo(username, password, false, true);
        // 发送注册请求
        HttpService.getInstance().getAPI(AuthApi.class)
                .register(username, password, email)
                .enqueue(new MvpNetworkCallBack<Void>(registerView) {
                    @Override
                    public void onSuccess(Void responseData) {
                        // 保存登录信息
                        DAOService.getInstance().saveLogInfo(logInfo);
                        // 跳转至主视图
                        registerView.toMainView();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        registerView.showHint(errorMessage);
                    }

                    @Override
                    public void onFailure() {
                        registerView.showHint("网络错误");
                    }
                });
    }

    /**
     * 加载已保存的登录信息
     *
     * @param loginView 登录视图
     */
    public static void loadingLogInfo(LoginView loginView) {
        // 从本地数据库获取登录信息
        LogInfo logInfo = DAOService.getInstance().getCurrentLogInfo();
        if (logInfo != null) {
            // 不为空则填写到相应文本框
            loginView.setUsername(logInfo.getUsername());
            loginView.setPassword(logInfo.getPassword());
            loginView.setSavePassword(logInfo.isSavePassword());
            loginView.setAutoLogin(logInfo.isAutoLogin());
        }
    }

    /**
     * 登录
     *
     * @param loginView 登录视图
     */
    public static void login(LoginView loginView) {
        // 显示登录加载条
        loginView.showLoading();
        // 发送登录请求
        HttpService.getInstance().getAPI(AuthApi.class)
                .login(loginView.getUsername(), loginView.getPassword())
                .enqueue(new MvpNetworkCallBack<Void>(loginView) {
                    @Override
                    public void onSuccess(Void responseData) {
                        // 隐藏加载条
                        loginView.hideLoading();
                        // 跳转至主视图
                        loginView.toMainView();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        Log.d(TAG, "登录错误，错误码：" + errorCode + "错误信息：" + errorMessage);
                        loginView.showHint(errorMessage);
                    }

                    @Override
                    public void onFailure() {
                        loginView.showHint("网络错误");
                    }
                });
    }

    /**
     * 自动登录
     *
     * @param autoLoginView 自动登录视图
     */
    public static void autoLogin(AutoLoginView autoLoginView) {
        // 从本地数据库获取保存的登录信息
        LogInfo logInfo = SingletonHolder.getInstance(DAOService.class).getCurrentLogInfo();
        if (logInfo != null && logInfo.isAutoLogin()) {
            // 发送登录请求
            HttpService.getInstance().getAPI(AuthApi.class)
                    .login(logInfo.getUsername(), logInfo.getPassword())
                    .enqueue(new MvpNetworkCallBack<Void>(autoLoginView) {
                        @Override
                        public void onSuccess(Void responseData) {
                            // 登录成功跳转至主视图
                            autoLoginView.toMainView();
                        }

                        // 失败跳转至登录视图
                        @Override
                        public void onError(int errorCode, String errorMessage) {
                            autoLoginView.toLoginView();
                        }

                        @Override
                        public void onFailure() {
                            autoLoginView.toLoginView();
                        }
                    });
        }
    }

    /**
     * 注销登录
     *
     * @param logoutView 注销登录视图
     */
    public static void logout(LogoutView logoutView) {
        // 发送注销登录请求
        HttpService.getInstance().getAPI(AuthApi.class)
                .logout().enqueue(new MvpNetworkCallBack<Void>(logoutView) {
            @Override
            public void onSuccess(Void responseData) {
                logoutView.toLoginView();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                logoutView.showToast(errorMessage);
            }

            @Override
            public void onFailure() {
                logoutView.showToast("网络错误");
            }
        });
    }
}
