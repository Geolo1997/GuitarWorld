package pers.geolo.guitarworldserver.controller.param;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/17
 */
public class WorksParam {

    private long worksId;
    private String author;
    private String follower;

    public long getWorksId() {
        return worksId;
    }

    public void setWorksId(long worksId) {
        this.worksId = worksId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }
}
