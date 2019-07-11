package pers.geolo.guitarworldserver.entity;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/11
 */
public class Music {

    private long id;
    private String name;
    private String author;
    private String profile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
