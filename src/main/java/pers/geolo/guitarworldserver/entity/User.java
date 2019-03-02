package pers.geolo.guitarworldserver.entity;

import pers.geolo.guitarworldserver.value.LogState;

public class User {

    private String username;
    private String password;
    private String email;
    private LogState logState;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setState(LogState logState) {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LogState getLogState() {
        return logState;
    }

    public void setLogState(LogState logState) {
        this.logState = logState;
    }
}
