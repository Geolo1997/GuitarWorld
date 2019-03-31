package pers.geolo.guitarworld.presenter;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.AuthApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.AutoLoginView;
import pers.geolo.util.SingletonHolder;

public class AutoLoginPresenter extends BasePresenter<AutoLoginView> {

    /**
     * 自动登录
     */
    public void autoLogin() {
        // 从本地数据库获取保存的登录信息
        LogInfo logInfo = SingletonHolder.getInstance(DAOService.class).getCurrentLogInfo();
        if (logInfo != null && logInfo.isAutoLogin()) {
            // 发送登录请求
            HttpService.getInstance().getAPI(AuthApi.class)
                    .login(logInfo.getUsername(), logInfo.getPassword())
                    .enqueue(new MvpNetworkCallBack<Void>(getView()) {
                        @Override
                        public void onSuccess(Void responseData) {
                            // 登录成功跳转至主视图
                            getView().toMainView();
                        }

                        // 失败跳转至登录视图
                        @Override
                        public void onError(int errorCode, String errorMessage) {
                            getView().toLoginView();
                        }

                        @Override
                        public void onFailure() {
                            getView().toLoginView();
                        }
                    });
        } else {
            getView().toLoginView();
        }
    }
}
