package pers.jieao.guitarworld;

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
}