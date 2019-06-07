package pers.geolo.guitarworld.entity;


import java.util.List;

public class User {

    private String username;
    private String password;
    private String email;
    private String avatarImagePath;

    // 我的关注者（粉丝）
    private List<String> followingUsername;
    // 我关注的（偶像）
    private List<String> followerUsername;

    public User() {
    }

    public String getAvatarImagePath() {
        return avatarImagePath;
    }

    public void setAvatarImagePath(String avatarImagePath) {
        this.avatarImagePath = avatarImagePath;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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
