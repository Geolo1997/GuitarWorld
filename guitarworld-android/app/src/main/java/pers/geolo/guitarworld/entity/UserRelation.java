package pers.geolo.guitarworld.entity;

public class UserRelation {


    // 粉丝用户名
    private String followerUsername;
    // 偶像用户名
    private String followingUsername;

    public UserRelation() {
    }

    public UserRelation(String followerUsername, String followingUsername) {
        this.followerUsername = followerUsername;
        this.followingUsername = followingUsername;
    }


    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    public String getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }


    @Override
    public String toString() {
        return "UserRelation{" +
                "followerUsername='" + followerUsername + '\'' +
                ", followingUsername='" + followingUsername + '\'' +
                '}';
    }
}
