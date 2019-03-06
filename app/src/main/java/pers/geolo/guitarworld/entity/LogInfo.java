package pers.geolo.guitarworld.entity;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * 登录信息
 */
public class LogInfo extends DataSupport {

    private String username;
    private String password;
    private boolean isSavePassword;
    private boolean autoLogin;

    private Date saveTime;

    public LogInfo() {
    }

    public LogInfo(String username, String password, boolean isSavePassword, boolean autoLogin) {
        this.username = username;
        this.password = password;
        this.isSavePassword = isSavePassword;
        this.autoLogin = autoLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSavePassword() {
        return isSavePassword;
    }

    public void setSavePassword(boolean savePassword) {
        isSavePassword = savePassword;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }
}
