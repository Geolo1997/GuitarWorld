package pers.geolo.guitarworldserver.entity;

public class UserRelation {


    // 粉丝用户名
    private String followerUsername;
    // 偶像用户名
    private String followingUsername;
    // 用户关系
    private UserRelationType relationType;

    public UserRelation() {
    }

    public UserRelation(String followerUsername, String followingUsername) {
        this.followerUsername = followerUsername;
        this.followingUsername = followingUsername;
    }

    public UserRelation(String followerUsername, String followingUsername, UserRelationType relationType) {
        this.followerUsername = followerUsername;
        this.followingUsername = followingUsername;
        this.relationType = relationType;
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

    public UserRelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(UserRelationType relationType) {
        this.relationType = relationType;
    }

    @Override
    public String toString() {
        return "UserRelation{" +
                "followerUsername='" + followerUsername + '\'' +
                ", followingUsername='" + followingUsername + '\'' +
                '}';
    }
}
