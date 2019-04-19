package pers.geolo.guitarworld.presenter.auth;

import android.util.Log;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.view.LoginView;

public class LoginPresenter extends BasePresenter<LoginView> {

    /**
     * 加载已保存的登录信息
     */
    public void loadingLogInfo() {
        // 从本地数据库获取登录信息
        LogInfo logInfo = daoService.getCurrentLogInfo();
        if (logInfo != null) {
            // 不为空则填写到相应文本框
            getView().setUsername(logInfo.getUsername());
            getView().setPassword(logInfo.getPassword());
            getView().setSavePassword(logInfo.isSavePassword());
            getView().setAutoLogin(logInfo.isAutoLogin());
        }
    }

    /**
     * 登录
     */
    public void login() {
        // 显示登录加载条
        getView().showLoading();
        // 保存登录信息
        LogInfo logInfo = new LogInfo();
        logInfo.setUsername(getView().getUsername());
        if (getView().isAutoLogin() || getView().isSavePassword()) {
            logInfo.setPassword(getView().getPassword());
        }
        logInfo.setSavePassword(getView().isSavePassword());
        logInfo.setAutoLogin(getView().isAutoLogin());
        // 发送登录请求
        authApi.login(getView().getUsername(), getView().getPassword())
                .enqueue(new MvpCallBack<Void>(getView()) {
                    @Override
                    public void onSuccess(Void responseData) {
                        // 保存登录信息到数据库
                        DAOService daoService = DAOService.getInstance();
                        if (daoService.getLogInfo(logInfo.getUsername()) != null) {
                            daoService.updateLogInfo(logInfo);
                        } else {
                            daoService.addLogInfo(logInfo);
                        }
                        // 保存登录信息到CustomContext
                        CustomContext.getInstance().setLogInfo(logInfo);
                        // 隐藏加载条
                        getView().hideLoading();
                        // 跳转至主视图
                        getView().toMainView();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        Log.d(TAG, "登录错误，错误码：" + errorCode + "错误信息：" + errorMessage);
                        getView().showHint("账号或密码错误");
                    }

                    @Override
                    public void onFailure() {
                        getView().showHint("网络错误");
                    }
                });
    }
}
