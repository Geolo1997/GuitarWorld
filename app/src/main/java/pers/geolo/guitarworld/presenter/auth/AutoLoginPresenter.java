package pers.geolo.guitarworld.presenter.auth;

import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.ui.base.CustomContext;
import pers.geolo.guitarworld.view.AutoLoginView;

public class AutoLoginPresenter extends BasePresenter<AutoLoginView> {

    /**
     * 自动登录
     */
    public void autoLogin() {
        // 从本地数据库获取保存的登录信息
        LogInfo logInfo = daoService.getCurrentLogInfo();
        if (logInfo != null && logInfo.isAutoLogin()) {
            // 发送登录请求
            authApi.login(logInfo.getUsername(), logInfo.getPassword())
                    .enqueue(new MvpCallBack<Void>(getView()) {
                        @Override
                        public void onSuccess(Void responseData) {
                            CustomContext.getInstance().setLogInfo(logInfo);
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
