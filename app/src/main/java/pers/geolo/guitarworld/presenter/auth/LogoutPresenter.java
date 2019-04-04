package pers.geolo.guitarworld.presenter.auth;

import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.AuthApi;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.view.LogoutView;

public class LogoutPresenter extends BasePresenter<LogoutView> {

    /**
     * 注销登录
     */
    public void logout() {
        // 发送注销登录请求
        HttpService.getInstance().getAPI(AuthApi.class)
                .logout().enqueue(new MvpCallBack<Void>(getView()) {
            @Override
            public void onSuccess(Void responseData) {
                // 自动登录设为false
                LogInfo logInfo = DAOService.getInstance().getCurrentLogInfo();
                logInfo.setAutoLogin(false);
                DAOService.getInstance().updateLogInfo(logInfo);
                // 从CustomContext中移除登录信息
                CustomContext.getInstance().setLogInfo(null);
                // 跳转至登录视图
                getView().toLoginView();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                getView().showToast(errorMessage);
            }

            @Override
            public void onFailure() {
                getView().showToast("网络错误");
            }
        });
    }
}
