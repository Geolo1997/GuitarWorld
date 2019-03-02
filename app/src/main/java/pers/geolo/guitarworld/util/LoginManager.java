package pers.geolo.guitarworld.util;

import pers.geolo.guitarworld.entity.User;

public class LoginManager {

    private boolean logged;

    private User loginUser;

    public LoginManager() {
        logged = false;
    }

    public boolean isLogged() {
        return logged;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
        if (loginUser != null) {
            logged = true;
        }
    }

    public void logout() {
        loginUser = null;
        logged = false;
    }
}