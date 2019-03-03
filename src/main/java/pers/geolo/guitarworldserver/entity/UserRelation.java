package pers.geolo.guitarworldserver.entity;

public class UserRelation {

    private String followingUsername;
    private String followerUsername;

    public UserRelation() {
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
}
