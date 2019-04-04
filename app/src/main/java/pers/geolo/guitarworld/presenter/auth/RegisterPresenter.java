package pers.geolo.guitarworld.presenter.auth;

import pers.geolo.guitarworld.dao.DAOService;
import pers.geolo.guitarworld.entity.LogInfo;
import pers.geolo.guitarworld.network.HttpService;
import pers.geolo.guitarworld.network.api.AuthApi;
import pers.geolo.guitarworld.network.callback.MvpCallBack;
import pers.geolo.guitarworld.presenter.base.BasePresenter;
import pers.geolo.guitarworld.view.RegisterView;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    /**
     * 注册
     */
    public void register() {
        // 从视图中获取注册信息
        String username = getView().getUsername();
        String password = getView().getPassword();
        String email = getView().getEmail();
        // 检查登录信息合法性
        if (username == null || password == null || email == null) {
            getView().showHint("请填写完整");
            return;
        } // TODO 检查用户名密码和邮箱字符串规则

        // 构建登录信息
        LogInfo logInfo = new LogInfo(username, password, false, true);
        // 发送注册请求
        HttpService.getInstance().getAPI(AuthApi.class)
                .register(username, password, email)
                .enqueue(new MvpCallBack<Void>(getView()) {
                    @Override
                    public void onSuccess(Void responseData) {
                        // 保存登录信息
                        DAOService.getInstance().addLogInfo(logInfo);
                        // 跳转至主视图
                        getView().toMainView();
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        getView().showHint(errorMessage);
                    }

                    @Override
                    public void onFailure() {
                        getView().showHint("网络错误");
                    }
                });
    }
}
