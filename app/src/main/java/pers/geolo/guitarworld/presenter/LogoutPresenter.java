package pers.geolo.guitarworld.presenter;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.AuthApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.LogoutView;

public class LogoutPresenter extends BasePresenter<LogoutView> {

    /**
     * 注销登录
     */
    public void logout() {
        // 发送注销登录请求
        HttpService.getInstance().getAPI(AuthApi.class)
                .logout().enqueue(new MvpNetworkCallBack<Void>(getView()) {
            @Override
            public void onSuccess(Void responseData) {
                // 自动登录设为false
                LogInfo logInfo = DAOService.getInstance().getCurrentLogInfo();
                logInfo.setAutoLogin(false);
                DAOService.getInstance().updateLogInfo(logInfo);
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
