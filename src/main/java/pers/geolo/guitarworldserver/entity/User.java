package pers.geolo.guitarworldserver.entity;

import pers.geolo.guitarworldserver.value.LogState;

import java.util.List;

public class User {

    private String username;
    private String password;
    private String email;
    private LogState logState;
    private String avatarPath;

    // 我的关注者（粉丝）
    private List<String> followingUsername;
    // 我关注的（偶像）
    private List<String> followerUsername;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getAvatarPath() {
        return avatarPath;
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

    public List<String> getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(List<String> followingUsername) {
        this.followingUsername = followingUsername;
    }

    public List<String> getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(List<String> followerUsername) {
        this.followerUsername = followerUsername;
    }
}
