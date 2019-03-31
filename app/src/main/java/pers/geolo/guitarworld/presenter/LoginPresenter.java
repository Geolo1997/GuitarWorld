package pers.geolo.guitarworld.presenter;

import android.util.Log;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.AuthApi;
import pers.geolo.guitarworld.network.callback.MvpNetworkCallBack;
import pers.geolo.guitarworld.view.AutoLoginView;
import pers.geolo.guitarworld.view.LoginView;
import pers.geolo.util.SingletonHolder;

public class LoginPresenter extends BasePresenter<LoginView> {

    /**
     * 加载已保存的登录信息
     */
    public void loadingLogInfo() {
        // 从本地数据库获取登录信息
        LogInfo logInfo = DAOService.getInstance().getCurrentLogInfo();
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
        // 发送登录请求
        HttpService.getInstance().getAPI(AuthApi.class)
                .login(getView().getUsername(), getView().getPassword())
                .enqueue(new MvpNetworkCallBack<Void>(getView()) {
                    @Override
                    public void onSuccess(Void responseData) {
                        // 隐藏加载条
                        getView().hideLoading();
                        // 跳转至主视图
                        getView().toMainView();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        Log.d(TAG, "登录错误，错误码：" + errorCode + "错误信息：" + errorMessage);
                        getView().showHint(errorMessage);
                    }

                    @Override
                    public void onFailure() {
                        getView().showHint("网络错误");
                    }
                });
    }


}
