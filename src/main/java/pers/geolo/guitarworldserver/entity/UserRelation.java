package pers.geolo.guitarworldserver.entity;

public class UserRelation {

    // 粉丝用户名
    private String followingUsername;
    // 偶像用户名
    private String followerUsername;

    public UserRelation() {
    }

    public UserRelation(String followingUsername, String followerUsername) {
        this.followingUsername = followingUsername;
        this.followerUsername = followerUsername;
    }

    public String getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    @Override
    public String toString() {
        return "UserRelation{" +
                "followingUsername='" + followingUsername + '\'' +
                ", followerUsername='" + followerUsername + '\'' +
                '}';
    }
}
