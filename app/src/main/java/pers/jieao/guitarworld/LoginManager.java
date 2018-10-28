package pers.jieao.guitarworld;

import pers.jieao.guitarworld.model.User;

public class LoginManager {

    private LoginManager() {
    }

    private static class Holder {
        private static final LoginManager INSTANCE = new LoginManager();
    }

    public static LoginManager getInstance() {
        return Holder.INSTANCE;
    }

    private LoginStatus mLoginStatus;
    private User mLoginUser;
    /**
     * 获取登录状态
     *
     * @return
     */
    public boolean isLogined() {
        return true;
        /*
        if (mLoginStatus == LoginStatus.LOGINED) {
            return true;
        } else {
            return false;
        }*/
    }

    public void setLoginStatus(LoginStatus loginStatus, String id, String password) {
        mLoginStatus = loginStatus;
        mLoginUser = new User(id, password);
    }

    public String getUserId() {
        return mLoginUser.getId();
    }
}