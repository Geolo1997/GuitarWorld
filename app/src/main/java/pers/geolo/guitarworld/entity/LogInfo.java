package pers.geolo.guitarworld.entity;

import java.util.Date;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 登录信息
 */
public class LogInfo extends LitePalSupport {

    @Column(unique = true)
    private String username;

    private String password;

    @Column(defaultValue = "0")
    private boolean savePassword;

    @Column(defaultValue = "0")
    private boolean autoLogin;

    private Date saveTime;

    public LogInfo() {
    }

    public LogInfo(String username, String password, boolean savePassword, boolean autoLogin) {
        this.username = username;
        this.password = password;
        this.savePassword = savePassword;
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
        return savePassword;
    }

    public void setSavePassword(boolean savePassword) {
        this.savePassword = savePassword;
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
