package pers.geolo.guitarworldserver.entity;

import java.util.Date;

public class Comment {

    private int id;
    private int worksId;
    private String author;
    private Date createTime;
    private String content;

    public Comment() {
    }

    public Comment(int id, int worksId, String author, Date createTime, String content) {
        this.id = id;
        this.worksId = worksId;
        this.author = author;
        this.createTime = createTime;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorksId() {
        return worksId;
    }

    public void setWorksId(int worksId) {
        this.worksId = worksId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", worksId=" + worksId +
                ", author='" + author + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}
